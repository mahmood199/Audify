package com.example.data.repo.contracts

import com.example.data.models.remote.saavn.HomePageResponse
import com.example.data.remote.core.NetworkResult

interface LandingPageRepository {

    suspend fun getLandingPageData(): NetworkResult<HomePageResponse>

}