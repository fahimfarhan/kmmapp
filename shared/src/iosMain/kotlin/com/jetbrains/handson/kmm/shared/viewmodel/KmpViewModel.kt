package com.jetbrains.handson.kmm.shared.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

actual abstract class KmpViewModel {
    actual val viewModelScope: CoroutineScope = MainScope()

    protected actual open fun onCleared() {}

    fun clear() {
        onCleared()
        viewModelScope.cancel()
    }
}