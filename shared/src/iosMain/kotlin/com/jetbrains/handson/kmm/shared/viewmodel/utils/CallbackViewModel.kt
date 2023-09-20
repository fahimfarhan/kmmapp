package com.jetbrains.handson.kmm.shared.viewmodel.utils

import com.jetbrains.handson.kmm.shared.viewmodel.KmpViewModel
import kotlinx.coroutines.flow.Flow

abstract class CallbackViewModel {
    protected abstract val kmpViewModel: KmpViewModel

    /**
     * Create a [FlowAdapter] from this [Flow] to make it easier to interact with from Swift.
     */
    fun <T : Any> Flow<T>.asCallbacks() =
        FlowAdapter(kmpViewModel.viewModelScope, this)

    @Suppress("Unused") // Called from Swift
    fun clear() = kmpViewModel.clear()
}