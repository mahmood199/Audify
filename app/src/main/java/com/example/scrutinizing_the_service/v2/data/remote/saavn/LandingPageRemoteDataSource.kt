package com.example.scrutinizing_the_service.v2.data.remote.saavn

import android.util.Log
import com.example.scrutinizing_the_service.v2.network.SaavnClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class LandingPageRemoteDataSource @Inject constructor(
    private val client: SaavnClient
) {

    suspend fun getHomePageData() {
        val response = client().get(
            "https://saavn.me/modules?language=hindi,english"
        ) {

        }
        Log.d("LandingPage", response.body<String>())
    }

}