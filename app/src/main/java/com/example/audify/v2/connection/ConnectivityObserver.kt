package com.example.audify.v2.connection

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    val networkState: Flow<Boolean>

    val connected: Boolean

    val disconnected: Boolean

}
