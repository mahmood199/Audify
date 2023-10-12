package com.example.scrutinizing_the_service.v2.data.remote.saavn

import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.ArtistDetailResponse
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.HomePageResponse
import com.example.scrutinizing_the_service.v2.network.NetworkResult
import com.example.scrutinizing_the_service.v2.network.ResponseProcessor
import com.example.scrutinizing_the_service.v2.network.SaavnClient
import com.google.gson.Gson
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class LandingPageRemoteDataSource @Inject constructor(
    private val responseProcessor: ResponseProcessor,
    private val client: SaavnClient,
    private val gson: Gson
) {

    suspend fun getHomePageData(): NetworkResult<HomePageResponse> {
        val response = client().get(
            "https://saavn.me/modules?trending"
        ) {

        }
        return responseProcessor.getResultFromResponse<HomePageResponse>(gson, response)
    }

    suspend fun getArtistData(artistUrl: String): NetworkResult<ArtistDetailResponse> {
        val response = client().get(
            "https://saavn.me/artists"
        ) {
            parameter("link", artistUrl)
        }
        return responseProcessor.getResultFromResponse<ArtistDetailResponse>(gson, response)
    }

}