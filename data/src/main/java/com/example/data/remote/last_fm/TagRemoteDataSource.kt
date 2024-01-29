package com.example.data.remote.last_fm

import com.example.data.models.remote.last_fm.TagListResponse
import com.example.data.models.remote.last_fm.TagResponse
import com.example.data.remote.core.LastFmClient
import com.example.data.remote.core.NetworkResult
import com.example.data.remote.core.ResponseProcessor
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class TagRemoteDataSource @Inject constructor(
    @LastFmClient private val httpClient: HttpClient,
    private val responseProcessor: ResponseProcessor,
    private val gson: Gson
) {

    suspend fun getAllTags(): NetworkResult<TagListResponse> {
        val response = httpClient.get {
            parameter("method", "tag.gettoptags")
        }
        return responseProcessor.getResultFromResponse<TagListResponse>(gson, response)
    }

    suspend fun getTagInfo(tag: String): NetworkResult<TagResponse> {
        val response = httpClient.get {
            parameter("method", "tag.getinfo")
            parameter("tag", tag)
        }
        return responseProcessor.getResultFromResponse<TagResponse>(gson, response)
    }

}