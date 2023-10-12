package com.example.scrutinizing_the_service.v2.data.remote.last_fm

import com.example.scrutinizing_the_service.v2.data.models.remote.last_fm.TagListResponse
import com.example.scrutinizing_the_service.v2.data.models.remote.last_fm.TagResponse
import com.example.scrutinizing_the_service.v2.network.LastFmClient
import com.example.scrutinizing_the_service.v2.network.NetworkResult
import com.example.scrutinizing_the_service.v2.network.ResponseProcessor
import com.google.gson.Gson
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class TagRemoteDataSource @Inject constructor(
    private val httpClient: LastFmClient,
    private val responseProcessor: ResponseProcessor,
    private val gson: Gson
) {

    suspend fun getAllTags(): NetworkResult<TagListResponse> {
        val response = httpClient().get {
            parameter("method", "tag.gettoptags")
        }
        return responseProcessor.getResultFromResponse<TagListResponse>(gson, response)
    }

    suspend fun getTagInfo(tag: String): NetworkResult<TagResponse> {
        val response = httpClient().get {
            parameter("method", "tag.getinfo")
            parameter("tag", tag)
        }
        return responseProcessor.getResultFromResponse<TagResponse>(gson, response)
    }

}