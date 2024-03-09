package com.example.data.repo.implementations

import com.example.data.local.datasource.ArtistsLocalDataSource
import com.example.data.mapper.ArtistsMapper
import com.example.data.models.local.Artist2
import com.example.data.models.remote.saavn.ArtistDetailResponse
import com.example.data.remote.core.NetworkResult
import com.example.data.remote.saavn.ArtistsRemoteDataSource
import com.example.data.repo.contracts.ArtistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ArtistsRepositoryImpl @Inject constructor(
    private val localDataSource: ArtistsLocalDataSource,
    private val remoteDataSource: ArtistsRemoteDataSource,
    private val mapper: ArtistsMapper
) : ArtistsRepository {

    override fun getAll(): Flow<List<Artist2>> {
        return localDataSource.getAllAsFlow().distinctUntilChanged()
    }

    override suspend fun add(artist2: Artist2): Long {
        return localDataSource.add(artist2 = artist2)
    }

    override suspend fun getById(artistId: String): Artist2? {
        return localDataSource.getById(id = artistId)
    }

    override suspend fun getArtistInfo(artistUrl: String): NetworkResult<ArtistDetailResponse> {
        val result = remoteDataSource.getArtistData(artistUrl = artistUrl)
        when(result) {
            is NetworkResult.Success -> {
                val artistData = result.data.data
                add(mapper.map(artistData))
            }
            else -> {}
        }
        return result
    }

}