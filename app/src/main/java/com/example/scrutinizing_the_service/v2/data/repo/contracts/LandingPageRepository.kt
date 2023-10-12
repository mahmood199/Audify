package com.example.scrutinizing_the_service.v2.data.repo.contracts

import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.ArtistDetailResponse
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.HomePageResponse
import com.example.scrutinizing_the_service.v2.network.NetworkResult

interface LandingPageRepository {

    suspend fun getLandingPageData(): NetworkResult<HomePageResponse>

    suspend fun getArtistInfo(artistUrl: String): NetworkResult<ArtistDetailResponse>

}