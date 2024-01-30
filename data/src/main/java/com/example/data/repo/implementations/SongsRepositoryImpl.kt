package com.example.data.repo.implementations

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.local.datasource.SongsLocalDataSource
import com.example.data.mapper.SongMapper
import com.example.data.models.local.DownloadItem
import com.example.data.models.local.RecentlyPlayed
import com.example.data.models.remote.saavn.SongsResponse
import com.example.data.remote.saavn.SongsRemoteDataSource
import com.example.data.repo.contracts.SongsRepository
import com.example.data.remote.core.NetworkResult
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

    fun observePagingSource(): Flow<PagingData<RecentlyPlayed>> {
        return Pager(
            config = PagingConfig(pageSize = 10, initialLoadSize = 20),
            pagingSourceFactory = { localDataSource.pagingSource() }
        ).flow
    }

    override fun observeMostPlayed(): Flow<List<RecentlyPlayed>> {
        return localDataSource.sortByMostPlayed()
    }

    override suspend fun getAll(): List<RecentlyPlayed> {
        return localDataSource.getAll()
    }

    override suspend fun insertSongs(songs: List<RecentlyPlayed>) {
        return localDataSource.insertSongs(songs)
    }

    override suspend fun updateFavourite(id: String, isFavorite: Boolean): Int {
        return localDataSource.updateFavorite(id, isFavorite)
    }

}