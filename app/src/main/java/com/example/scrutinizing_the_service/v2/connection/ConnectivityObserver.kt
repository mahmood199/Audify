package com.example.scrutinizing_the_service.v2.connection

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    val networkState: Flow<Boolean>

    val connected: Boolean

    val disconnected: Boolean

}
