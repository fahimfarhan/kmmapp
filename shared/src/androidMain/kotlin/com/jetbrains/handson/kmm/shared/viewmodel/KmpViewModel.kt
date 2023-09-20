package com.jetbrains.handson.kmm.shared.viewmodel

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import androidx.lifecycle.ViewModel as AndroidXViewModel
import androidx.lifecycle.viewModelScope as androidXViewModelScope

actual abstract class KmpViewModel:
    AndroidXViewModel() {

        actual val viewModelScope: CoroutineScope = androidXViewModelScope

    actual override fun onCleared() {
        super.onCleared()
    }
}