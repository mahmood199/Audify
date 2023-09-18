package com.example.scrutinizing_the_service.v2.local

sealed class LocalResult<T : Any> {
    class Success<T: Any>(val data: T) : LocalResult<T>()
    class Error<T: Any>(val code: Int, val message: String?) : LocalResult<T>()
    class Exception<T: Any>(val e: Throwable) : LocalResult<T>()
}