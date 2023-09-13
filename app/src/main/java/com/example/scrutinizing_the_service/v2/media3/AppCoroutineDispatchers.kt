package com.example.scrutinizing_the_service.v2.media3

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object AppCoroutineDispatchers {
    val io: CoroutineDispatcher = Dispatchers.IO
    val default: CoroutineDispatcher = Dispatchers.Default
    val main: CoroutineDispatcher = Dispatchers.Main

    private val unconfined: CoroutineDispatcher = Dispatchers.Unconfined

    val ioWithNetworkExceptionHandler
        get() = io + GeneralExceptionHandler.handler()
}
