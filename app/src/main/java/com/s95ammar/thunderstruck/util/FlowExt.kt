package com.s95ammar.thunderstruck.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

suspend fun<T> flowOnIo(flowBlock: suspend () -> Flow<T>) = flowBlock().flowOn(Dispatchers.IO)
