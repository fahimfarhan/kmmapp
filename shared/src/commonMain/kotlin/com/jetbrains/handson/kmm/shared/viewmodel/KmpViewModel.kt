package com.jetbrains.handson.kmm.shared.viewmodel

import kotlinx.coroutines.CoroutineScope

expect abstract class KmpViewModel() {

    val viewModelScope: CoroutineScope

    protected open fun onCleared()
}