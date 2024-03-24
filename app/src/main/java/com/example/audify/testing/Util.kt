package com.example.audify.testing

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Util(
    val dispatcher: CoroutineDispatcher
) {

    suspend fun getUserName(): String {
        delay(10000L)
        return "CoroutineTesting"
    }

    suspend fun getUser(): String {
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
        }
        return "User - CoroutineTesting"
    }

    suspend fun getAddress(): String {
        CoroutineScope(dispatcher).launch {
            delay(5000)
        }
        return "Address"
    }

    var globalArg = false
    fun getAddressDetails() {
        CoroutineScope(dispatcher).launch {
            globalArg = true
        }
    }

}