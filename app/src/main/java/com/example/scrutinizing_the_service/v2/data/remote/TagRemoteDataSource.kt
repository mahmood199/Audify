package com.example.scrutinizing_the_service.v2.data.remote

import com.example.scrutinizing_the_service.v2.data.models.remote.TagListResponse
import com.example.scrutinizing_the_service.v2.data.models.remote.TagResponse
import com.example.scrutinizing_the_service.v2.network.NetworkModule
import com.example.scrutinizing_the_service.v2.network.NetworkResult
import com.example.scrutinizing_the_service.v2.network.ResponseProcessor
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class TagRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient,
    private val responseProcessor: ResponseProcessor,
    private val gson: Gson
) {

    suspend fun getAllTags(): NetworkResult<TagListResponse> {
        val response = httpClient.get("${NetworkModule.BASE_URL}2.0") {
            parameter("method", "tag.gettoptags")
        }
        return responseProcessor.getResultFromResponse<TagListResponse>(gson, response)
    }

    suspend fun getTagInfo(tag: String): NetworkResult<TagResponse> {
        val response = httpClient.get("${NetworkModule.BASE_URL}2.0") {
            parameter("method", "tag.getinfo")
            parameter("tag", tag)
        }
        return responseProcessor.getResultFromResponse<TagResponse>(gson, response)
    }

}