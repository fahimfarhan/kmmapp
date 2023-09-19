package com.example.kmmapp.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.kmmapp.Greeting
import com.example.kmmapp.android.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    private val mText: MutableStateFlow<String> = MutableStateFlow("Loading...")

    private val mGreeting by lazy { Greeting() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObserver()
        makeApiCall()
    }

    private fun makeApiCall() {
        lifecycleScope.launch(Dispatchers.IO) {
            val responseText: String = try {
                delay(5000)
                mGreeting.greet()
            } catch (x: Exception) {
                x.printStackTrace()
                x.localizedMessage ?: "error"
            }
            mText.emit(responseText)
        }
    }
    private fun initObserver() {
        lifecycleScope.launch(Dispatchers.Main) {
            mText.collect { msg: String ->
                binding.textView.text = msg
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

