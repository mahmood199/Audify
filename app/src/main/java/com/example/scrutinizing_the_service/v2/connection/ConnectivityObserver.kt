package com.example.scrutinizing_the_service.v2.connection

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>

}
