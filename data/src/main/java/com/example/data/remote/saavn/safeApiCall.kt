package com.example.data.remote.saavn

import com.example.data.remote.core.NetworkResult
import java.io.IOException

inline fun <R : Any> safeApiCall(
    apiCall: () -> NetworkResult<R>
): NetworkResult<R> {
    return try {
        apiCall()
    } catch (e: Exception) {
        e.printStackTrace()
        if (e is IOException) {
            NetworkResult.Exception(Throwable("Internet not available"))
        } else
            NetworkResult.Exception(Throwable("Something went wrong"))
    }
}