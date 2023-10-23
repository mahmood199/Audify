package com.example.scrutinizing_the_service.v2.data.remote.saavn

import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.HomePageResponse
import com.example.scrutinizing_the_service.v2.data.remote.core.NetworkResult
import com.example.scrutinizing_the_service.v2.data.remote.core.ResponseProcessor
import com.example.scrutinizing_the_service.v2.data.remote.core.SaavnClient
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class LandingPageRemoteDataSource @Inject constructor(
    private val responseProcessor: ResponseProcessor,
    @SaavnClient private val client: HttpClient,
    private val gson: Gson
) {

    suspend fun getHomePageData(): NetworkResult<HomePageResponse> {
        return safeApiCall {
            val response = client.get(
                "https://saavn.me/modules?trending"
            ) {

            }
            return responseProcessor.getResultFromResponse<HomePageResponse>(gson, response)
        }
    }

}