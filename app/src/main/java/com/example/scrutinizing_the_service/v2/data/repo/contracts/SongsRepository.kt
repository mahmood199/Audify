package com.example.scrutinizing_the_service.v2.data.repo.contracts

import com.example.scrutinizing_the_service.v2.data.models.local.RecentlyPlayed
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Song
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.SongsResponse
import com.example.scrutinizing_the_service.v2.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface SongsRepository {

    suspend fun getSongsByGenres(genre: String)

    suspend fun getSongsByGenresTesting(genre: String): NetworkResult<SongsResponse>

    suspend fun getAll(): List<RecentlyPlayed>

    fun observeMostRecentlyPlayed(): Flow<List<RecentlyPlayed>>

    fun observeMostPlayed(): Flow<List<RecentlyPlayed>>

}