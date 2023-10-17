package com.example.scrutinizing_the_service.v2.data.remote.saavn

import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.SongsResponse
import com.example.scrutinizing_the_service.v2.data.remote.core.NetworkResult
import com.example.scrutinizing_the_service.v2.data.remote.core.ResponseProcessor
import com.example.scrutinizing_the_service.v2.data.remote.core.SaavnClient
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class SongsRemoteDataSource @Inject constructor(
    private val responseProcessor: ResponseProcessor,
    @SaavnClient private val client: HttpClient,
    private val gson: Gson,
) {

    suspend fun getSongsByGenres(genre: String): NetworkResult<SongsResponse> {
        val response = client.get(
            "https://saavn.me/search/songs"
        ) {
            parameter("query", genre)
        }
        return responseProcessor.getResultFromResponse<SongsResponse>(gson, response)
    }

}