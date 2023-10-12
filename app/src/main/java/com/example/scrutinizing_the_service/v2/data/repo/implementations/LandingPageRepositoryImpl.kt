package com.example.scrutinizing_the_service.v2.data.repo.implementations

import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.HomePageResponse
import com.example.scrutinizing_the_service.v2.data.remote.saavn.LandingPageRemoteDataSource
import com.example.scrutinizing_the_service.v2.data.repo.contracts.LandingPageRepository
import com.example.scrutinizing_the_service.v2.network.NetworkResult
import javax.inject.Inject

class LandingPageRepositoryImpl @Inject constructor(
    private val remoteDataSource: LandingPageRemoteDataSource
) : LandingPageRepository {

    override suspend fun getLandingPageData(): NetworkResult<HomePageResponse> {
        return remoteDataSource.getHomePageData()
    }

}