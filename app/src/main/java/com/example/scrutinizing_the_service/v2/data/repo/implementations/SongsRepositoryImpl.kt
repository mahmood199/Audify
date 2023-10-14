package com.example.scrutinizing_the_service.v2.data.repo.implementations

import android.util.Log
import com.example.scrutinizing_the_service.v2.data.local.datasource.SongsLocalDataSource
import com.example.scrutinizing_the_service.v2.data.mapper.SongMapper
import com.example.scrutinizing_the_service.v2.data.models.local.RecentlyPlayed
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.SongsResponse
import com.example.scrutinizing_the_service.v2.data.remote.saavn.SongsRemoteDataSource
import com.example.scrutinizing_the_service.v2.data.repo.contracts.SongsRepository
import com.example.scrutinizing_the_service.v2.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SongsRepositoryImpl @Inject constructor(
    private val remoteDataSource: SongsRemoteDataSource,
    private val localDataSource: SongsLocalDataSource,
    private val mapper: SongMapper
) : SongsRepository {

    override suspend fun getSongsByGenres(genre: String) {
        when (val result = remoteDataSource.getSongsByGenres(genre)) {
            is NetworkResult.Success -> {
                localDataSource.insertSongs(
                    result.data.data.results.map {
                        mapper.map(it)
                    }
                )
            }

            else -> {}
        }
    }

    override suspend fun getSongsByGenresTesting(genre: String): NetworkResult<SongsResponse> {
        return when (val result = remoteDataSource.getSongsByGenres(genre)) {
            is NetworkResult.Success -> {
                val data = result.data.data.results.map {
                    mapper.map(it)
                }
                Log.d("SongsRepository", data.size.toString())
                NetworkResult.Success(result.data)
            }

            else -> {
                result
            }
        }
    }

    override fun observeMostRecentlyPlayed(): Flow<List<RecentlyPlayed>> {
        return localDataSource.sortByMostRecentlyPlayed()
    }

    override fun observeMostPlayed(): Flow<List<RecentlyPlayed>> {
        return localDataSource.sortByMostPlayed()
    }

    override suspend fun getAll(): List<RecentlyPlayed> {
        return localDataSource.getAll()
    }

}