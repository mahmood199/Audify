package com.example.scrutinizing_the_service.v2.data.remote.last_fm

import com.example.scrutinizing_the_service.v2.data.models.remote.TrackListResponse
import com.example.scrutinizing_the_service.v2.network.NetworkModule
import com.example.scrutinizing_the_service.v2.network.NetworkResult
import com.example.scrutinizing_the_service.v2.network.ResponseProcessor
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class TrackRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient,
    private val responseProcessor: ResponseProcessor,
    private val gson: Gson
) {

    suspend fun getTracksByTag(tag: String): NetworkResult<TrackListResponse> {
        val response = httpClient.get("${NetworkModule.BASE_URL}2.0") {
            parameter("method", "tag.gettoptracks")
            parameter("tag", tag)
        }
        return responseProcessor.getResultFromResponse<TrackListResponse>(gson, response)
    }

}