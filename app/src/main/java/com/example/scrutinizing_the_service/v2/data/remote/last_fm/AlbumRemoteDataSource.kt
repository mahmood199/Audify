package com.example.scrutinizing_the_service.v2.data.remote.last_fm

import com.example.scrutinizing_the_service.v2.data.models.remote.AlbumListResponse
import com.example.scrutinizing_the_service.v2.network.LastFmClient
import com.example.scrutinizing_the_service.v2.network.NetworkResult
import com.example.scrutinizing_the_service.v2.network.ResponseProcessor
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