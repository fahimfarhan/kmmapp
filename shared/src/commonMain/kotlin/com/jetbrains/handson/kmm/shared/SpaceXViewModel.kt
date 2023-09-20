package com.jetbrains.handson.kmm.shared

import com.example.kmmapp.model.RocketLaunch
import com.jetbrains.handson.kmm.shared.viewmodel.BaseViewModel
import com.jetbrains.handson.kmm.shared.viewmodel.FlowAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SpaceXViewModel(
    private val spacexRepository: SpaceXSDK
): BaseViewModel() {

    companion object {
        var staticInstanceCount: Int = 0
    }

    init {
        staticInstanceCount++
        println("SpaceXViewModel staticInstanceCount = $staticInstanceCount")
    }

//    private val vmCallBack = VmCallBack(this)

    private val _flowOfRocketLaunches: MutableStateFlow<List<RocketLaunch>> by lazy {
        MutableStateFlow<List<RocketLaunch>>(emptyList())
    }
    val flowOfRocketLaunches: FlowAdapter<List<RocketLaunch>> get() = _flowOfRocketLaunches.asFlowAdapterCallbacks()

    val liveErrorMsg: FlowAdapter<String> = _liveErrorMsg.asFlowAdapterCallbacks()

    fun asyncGetSpacexRocketLaunches(forceReload: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                val output = spacexRepository.getLaunches(forceReload = forceReload)
                println("inside runCatching: output = $output")
                output
            }.onSuccess { listOfRockedLaunches ->
                println("inside onSuccess: listOfRocketLaunches: $listOfRockedLaunches")
                _flowOfRocketLaunches.emit(listOfRockedLaunches)
            }
            .onFailure {throwable ->
                println("onFailure: throwableMsg: ${throwable.message}")
                throwable.printStackTrace()
                _liveErrorMsg.emit(throwable.message ?: "Unknown Error")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}