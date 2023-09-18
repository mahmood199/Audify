package com.example.scrutinizing_the_service.v2.data.remote

import com.example.scrutinizing_the_service.v2.data.models.response.AlbumListResponse
import com.example.scrutinizing_the_service.v2.network.NetworkWrapper
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class DemoRemoteDataSource @Inject constructor(
    private val networkWrapper: NetworkWrapper
) {

    suspend fun getTopAlbums(): AlbumListResponse {
        return networkWrapper.makeRequest()
    }

}