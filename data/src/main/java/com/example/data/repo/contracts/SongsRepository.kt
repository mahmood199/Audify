package com.example.data.repo.contracts

import com.example.data.models.local.RecentlyPlayed
import com.example.data.models.remote.saavn.SongsResponse
import com.example.data.remote.core.NetworkResult
import kotlinx.coroutines.flow.Flow

interface SongsRepository {

    suspend fun getSongsByGenres(genre: String)

    suspend fun getSongsByGenresTesting(genre: String): NetworkResult<SongsResponse>

    suspend fun getAll(): List<RecentlyPlayed>

    suspend fun insertSongs(songs: List<RecentlyPlayed>)

    fun observeMostRecentlyPlayed(): Flow<List<RecentlyPlayed>>

    fun observeMostPlayed(): Flow<List<RecentlyPlayed>>

}