package com.jetbrains.handson.kmm.shared

import com.jetbrains.handson.kmm.shared.viewmodel.KmpViewModel
import com.jetbrains.handson.kmm.shared.viewmodel.utils.CallbackViewModel

class SpaceXCallBackViewModel(private val spaceXViewModel: SpaceXViewModel): CallbackViewModel() {

    override val kmpViewModel: KmpViewModel get() = spaceXViewModel

    val flowOfRocketLaunches get() = spaceXViewModel.flowOfRocketLaunches.asCallbacks()

    val liveErrorMsg get() = spaceXViewModel.liveErrorMsg.asCallbacks()
}
