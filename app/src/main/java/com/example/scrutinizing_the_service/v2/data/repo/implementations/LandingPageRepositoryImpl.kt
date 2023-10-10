package com.example.scrutinizing_the_service.v2.data.repo.implementations

import com.example.scrutinizing_the_service.v2.data.remote.saavn.LandingPageRemoteDataSource
import com.example.scrutinizing_the_service.v2.data.repo.contracts.LandingPageRepository
import javax.inject.Inject

class LandingPageRepositoryImpl @Inject constructor(
    private val remoteDataSource: LandingPageRemoteDataSource
) : LandingPageRepository {

    override suspend fun getLandingPageData() {
        remoteDataSource.getHomePageData()
    }

}