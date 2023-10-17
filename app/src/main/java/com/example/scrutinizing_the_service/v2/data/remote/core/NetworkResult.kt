package com.example.scrutinizing_the_service.v2.data.remote.core

sealed class NetworkResult<T : Any> {
    class Success<T: Any>(val data: T) : NetworkResult<T>()
    class UnAuthorised<T: Any>(val e: Throwable, val code: Int, val message: String?) : NetworkResult<T>()
    class RedirectError<T: Any>(val e: Throwable, val code: Int, val message: String?) : NetworkResult<T>()
    class ServerError<T: Any>(val e: Throwable, val code: Int, val message: String?) : NetworkResult<T>()
    class Exception<T: Any>(val e: Throwable) : NetworkResult<T>()
}