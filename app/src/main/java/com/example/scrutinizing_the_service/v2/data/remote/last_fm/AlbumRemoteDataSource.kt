package com.example.scrutinizing_the_service.v2.data.remote.last_fm

import com.example.scrutinizing_the_service.v2.data.models.remote.last_fm.AlbumListResponse
import com.example.scrutinizing_the_service.v2.data.remote.core.LastFmClient
import com.example.scrutinizing_the_service.v2.data.remote.core.NetworkResult
import com.example.scrutinizing_the_service.v2.data.remote.core.ResponseProcessor
import com.google.gson.Gson
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class AlbumRemoteDataSource @Inject constructor(
    private val httpClient: LastFmClient,
    private val responseProcessor: ResponseProcessor,
    private val gson: Gson
) {

    suspend fun getTopAlbums(): NetworkResult<AlbumListResponse> {
        val response = httpClient().get {
            parameter("method", "tag.gettopalbums")
        }
        return responseProcessor.getResultFromResponse<AlbumListResponse>(gson, response)
    }

    companion object {

    }

}