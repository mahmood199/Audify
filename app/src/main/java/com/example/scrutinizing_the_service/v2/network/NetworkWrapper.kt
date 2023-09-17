package com.example.scrutinizing_the_service.v2.network

import android.util.Log
import com.example.scrutinizing_the_service.v2.network.NetworkModule.Companion.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class NetworkWrapper @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun makeRequest(): HttpResponse {
        Log.d("CursedLog", "${BASE_URL}/2.0/?method=tag.gettopalbums")
        val response =
            httpClient.get(urlString = "${BASE_URL}/2.0/?method=tag.gettopalbums") {
            }
        Log.d("CursedLog", response.body<String>().toString())
        return response
    }

}