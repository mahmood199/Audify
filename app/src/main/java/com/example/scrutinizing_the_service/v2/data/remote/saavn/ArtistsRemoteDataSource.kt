package com.example.scrutinizing_the_service.v2.data.remote.saavn

import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.ArtistDetailResponse
import com.example.scrutinizing_the_service.v2.data.remote.core.NetworkResult
import com.example.scrutinizing_the_service.v2.data.remote.core.ResponseProcessor
import com.example.scrutinizing_the_service.v2.data.remote.core.SaavnClient
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class ArtistsRemoteDataSource @Inject constructor(
    private val responseProcessor: ResponseProcessor,
    @SaavnClient private val client: HttpClient,
    private val gson: Gson
) {

    suspend fun getArtistData(artistUrl: String): NetworkResult<ArtistDetailResponse> {
        val response = client.get(
            "https://saavn.me/artists"
        ) {
            parameter("link", artistUrl)
        }
        return responseProcessor.getResultFromResponse<ArtistDetailResponse>(gson, response)
    }

}