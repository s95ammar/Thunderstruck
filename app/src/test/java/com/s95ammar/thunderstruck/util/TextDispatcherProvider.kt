package com.s95ammar.thunderstruck.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object TestDispatcherProvider : DispatcherProvider {

    override val main: CoroutineDispatcher get() = Dispatchers.Main
    override val default: CoroutineDispatcher get() = Dispatchers.Main
    override val io: CoroutineDispatcher get() = Dispatchers.Main
    override val unconfined: CoroutineDispatcher get() = Dispatchers.Main
}
