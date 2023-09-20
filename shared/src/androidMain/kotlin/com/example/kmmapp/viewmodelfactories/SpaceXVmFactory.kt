package com.example.kmmapp.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jetbrains.handson.kmm.shared.SpaceXSDK
import com.jetbrains.handson.kmm.shared.SpaceXViewModel

class SpaceXVmFactory(private val spacexRepository: SpaceXSDK): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SpaceXViewModel(spacexRepository) as T
    }
}