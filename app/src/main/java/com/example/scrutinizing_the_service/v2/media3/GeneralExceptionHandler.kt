package com.example.scrutinizing_the_service.v2.media3

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

class GeneralExceptionHandler {

    companion object {
        fun handler() = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e("ERROR", "$coroutineContext ${throwable.printStackTrace()}")
        }
    }

}