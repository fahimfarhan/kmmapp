package com.jetbrains.handson.kmm.shared

import com.example.kmmapp.model.RocketLaunch
import com.jetbrains.handson.kmm.shared.viewmodel.KmpViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SpaceXViewModel(
    private val spacexRepository: SpaceXSDK
): KmpViewModel() {

    companion object {
        var staticInstanceCount: Int = 0
    }

    init {
        staticInstanceCount++
        println("SpaceXViewModel staticInstanceCount = $staticInstanceCount")
    }

    val flowOfRocketLaunches: MutableStateFlow<List<RocketLaunch>> by lazy {
        MutableStateFlow<List<RocketLaunch>>(emptyList())
    }

    val liveErrorMsg: MutableStateFlow<String> by lazy {
        MutableStateFlow("")
    }

    fun asyncGetSpacexRocketLaunches(forceReload: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                val output = spacexRepository.getLaunches(forceReload = forceReload)
                println("inside runCatching: output = $output")
                output
            }.onSuccess { listOfRockedLaunches ->
                println("inside onSuccess: listOfRocketLaunches: $listOfRockedLaunches")
                flowOfRocketLaunches.emit(listOfRockedLaunches)
            }
            .onFailure {throwable ->
                println("onFailure: throwableMsg: ${throwable.message}")
                throwable.printStackTrace()
                liveErrorMsg.emit(throwable.message ?: "Unknown Error")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}