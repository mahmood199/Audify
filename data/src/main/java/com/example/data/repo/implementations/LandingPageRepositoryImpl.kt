package com.example.data.repo.implementations

import com.example.data.models.remote.saavn.HomePageResponse
import com.example.data.remote.core.NetworkResult
import com.example.data.remote.saavn.LandingPageRemoteDataSource
import com.example.data.repo.contracts.LandingPageRepository
import javax.inject.Inject

class LandingPageRepositoryImpl @Inject constructor(
    private val remoteDataSource: LandingPageRemoteDataSource
) : LandingPageRepository {

    override suspend fun getLandingPageData(): NetworkResult<HomePageResponse> {
        return remoteDataSource.getHomePageData()
    }

}