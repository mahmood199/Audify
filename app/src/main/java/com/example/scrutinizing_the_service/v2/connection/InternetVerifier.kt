package com.example.scrutinizing_the_service.v2.connection

import android.util.Log
import io.ktor.utils.io.printStack
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject

class InternetVerifier @Inject constructor() {

    private val socket by lazy {
        Socket()
    }

    operator fun invoke(): Boolean {
        return try {
            Log.d(TAG, "Pinging network")
            socket.connect(InetSocketAddress(HOST_NAME, PORT_NUMBER), 500)
            socket.close()
            Log.d(TAG, "Ping Success")
            true
        } catch (e: Exception) {
            Log.d(TAG, "Internet Connection Not available")
            e.printStack()
            false
        }
    }

    companion object {
        const val TAG = "InternetVerifier"
        const val HOST_NAME = "8.8.8.8"
        const val PORT_NUMBER = 53
    }

}