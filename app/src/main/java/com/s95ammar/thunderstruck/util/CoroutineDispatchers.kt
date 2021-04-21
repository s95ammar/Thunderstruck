package com.s95ammar.thunderstruck.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

interface DispatcherProvider {

    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}

@Singleton
class DefaultDispatcherProvider @Inject constructor() : DispatcherProvider {

    override val main: CoroutineDispatcher get() = Dispatchers.Main
    override val default: CoroutineDispatcher get() = Dispatchers.Default
    override val io: CoroutineDispatcher get() = Dispatchers.IO
    override val unconfined: CoroutineDispatcher get() = Dispatchers.Unconfined
}
