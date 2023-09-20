package com.jetbrains.handson.kmm.shared.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

open class BaseViewModel: KmpViewModel() {
    fun <T : Any> Flow<T>.asFlowAdapterCallbacks(someScope: CoroutineScope = viewModelScope) = FlowAdapter(someScope, this)


    val _liveErrorMsg: MutableStateFlow<String> by lazy {
        MutableStateFlow("")
    }

}