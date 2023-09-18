package com.example.scrutinizing_the_service.v2.network

import android.util.Log
import com.example.scrutinizing_the_service.v2.data.models.response.AlbumListResponse
import com.example.scrutinizing_the_service.v2.data.models.response.Rank
import com.example.scrutinizing_the_service.v2.network.NetworkModule.Companion.BASE_URL
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import javax.inject.Inject

class NetworkWrapper @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun makeRequest(): AlbumListResponse {
        val request = Rank("1304")
        val response1 = httpClient.get(buildUrl("2.0/?method=tag.gettopalbums")) {
        }

        val response2 = httpClient.post(buildUrl("2.0/?method=tag.gettopalbums")) {

        }
        val body = response1.body<String>()
        val zz = deserializeToClass<AlbumListResponse>(body)
        Log.d("DeserializedClass", zz.toString())
        return zz
    }

    private inline fun <reified T> deserializeToClass(response: String): T {
        return Gson().fromJson(response, T::class.java)
    }

    private fun buildUrl(suffix: String): String {
        return "${BASE_URL}${suffix}"
    }

}