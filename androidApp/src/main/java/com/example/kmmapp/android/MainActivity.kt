package com.example.kmmapp.android

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kmmapp.android.databinding.ActivityMainBinding
import com.example.kmmapp.viewmodelfactories.SpaceXVmFactory
import com.jetbrains.handson.kmm.shared.SpaceXSDK
import com.jetbrains.handson.kmm.shared.SpaceXViewModel
import com.jetbrains.handson.kmm.shared.cache.DatabaseDriverFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    private val launchesRvAdapter by lazy { LaunchesRvAdapter(emptyList()) }

    private val sdk by lazy {
        SpaceXSDK(DatabaseDriverFactory(application))
    }

    private val spacexFactory by lazy {
        SpaceXVmFactory(spacexRepository = sdk)
    }
    private val spacexViewModel: SpaceXViewModel by lazy {
        ViewModelProvider(this, spacexFactory)[SpaceXViewModel::class.java]
    }

    private val mainScope by lazy {
        MainScope()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "SpaceX Launches"

        binding.launchesListRv.adapter = launchesRvAdapter
        binding.launchesListRv.layoutManager = LinearLayoutManager(application)

        binding.swipeContainer.setOnRefreshListener {
            binding.swipeContainer.isRefreshing = false
            displayLaunches(true)
        }
        displayLaunches(false)

        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            spacexViewModel.liveErrorMsg.collectLatest { errorMsg ->
                println("Inside onError -> errorMsg: $errorMsg")
                if(errorMsg.isNullOrBlank()) {
                    return@collectLatest
                }
                lifecycleScope.launch(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, errorMsg, Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launch {
            spacexViewModel.flowOfRocketLaunches.collectLatest { rocketLaunches ->
//            lifecycleScope.launch(Dispatchers.Main) {
                launchesRvAdapter.launches = rocketLaunches
                launchesRvAdapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
                println("inside onSuccess -> output = $rocketLaunches")
//            }
            }
        }
    }

    private fun displayLaunches(needReload: Boolean) {
        binding.progressBar.visibility = View.GONE // cz demo app, less bullshit
        spacexViewModel.asyncGetSpacexRocketLaunches(needReload)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mainScope.cancel()
    }

}

