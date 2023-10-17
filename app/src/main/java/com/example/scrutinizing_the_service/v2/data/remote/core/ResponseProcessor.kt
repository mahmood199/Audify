package com.example.scrutinizing_the_service.v2.data.remote.core

import com.google.gson.Gson
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class ResponseProcessor @Inject constructor() {

    inline fun <reified T> deserializeToClass(gson: Gson, response: String): T {
        return gson.fromJson(response, T::class.java)
    }

    suspend inline fun <reified R : Any> getResultFromResponse(
        gson: Gson,
        output: HttpResponse
    ): NetworkResult<R> {
        // Handle null case here. Suppose the response is 200 but the content is null or
        // invalid parsing class is specified

        return when (val code = output.status.value) {
            in 200..201 -> NetworkResult.Success(
                data = deserializeToClass(gson, output.body<String>())
            )

            in 300..320 -> NetworkResult.RedirectError(
                e = Exception("Error Redirect"),
                code = code,
                message = "Error Redirect"
            )

            in 400..420 -> NetworkResult.UnAuthorised(
                e = Exception("Error Authentication"),
                code = code,
                message = "UnAuthorised Access"
            )

            in 500..520 -> NetworkResult.ServerError(
                e = Exception("Server Error"),
                code = code,
                message = "Server Error"
            )

            else -> {
                NetworkResult.Exception(Exception())
            }
        }
    }

}