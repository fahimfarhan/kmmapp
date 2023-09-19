package com.example.kmmapp.android

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kmmapp.Greeting
import com.example.kmmapp.android.databinding.ActivityMainBinding
import com.jetbrains.handson.kmm.shared.SpaceXSDK
import com.jetbrains.handson.kmm.shared.cache.DatabaseDriverFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    private val launchesRvAdapter by lazy { LaunchesRvAdapter(emptyList()) }

    private val sdk by lazy {
        SpaceXSDK(DatabaseDriverFactory(application))
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
        displayLaunches(true)
    }

    private fun displayLaunches(needReload: Boolean) {
        binding.progressBar.isVisible = true
        mainScope.launch {
            kotlin.runCatching {
                val output = sdk.getLaunches(needReload)
                println("inside MainActivity, output = $output")
                output
            }.onSuccess { listOfRocketLaunches ->
                launchesRvAdapter.launches = listOfRocketLaunches
                launchesRvAdapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
                println("inside onSuccess -> output = $listOfRocketLaunches")
            }.onFailure {
                Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
                println("inside onFailure error: ${it.localizedMessage}")
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mainScope.cancel()
    }

}

