package com.example.data.remote.last_fm

import com.example.data.models.remote.last_fm.AlbumListResponse
import com.example.data.remote.core.LastFmClient
import com.example.data.remote.core.NetworkResult
import com.example.data.remote.core.ResponseProcessor
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class AlbumRemoteDataSource @Inject constructor(
    @LastFmClient private val httpClient: HttpClient,
    private val responseProcessor: ResponseProcessor,
    private val gson: Gson
) {

    suspend fun getTopAlbums(): NetworkResult<AlbumListResponse> {
        val response = httpClient.get {
            parameter("method", "tag.gettopalbums")
        }
        return responseProcessor.getResultFromResponse<AlbumListResponse>(gson, response)
    }

    companion object {

    }

}