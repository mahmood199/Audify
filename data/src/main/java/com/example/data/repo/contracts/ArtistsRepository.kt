package com.example.data.repo.contracts

import com.example.data.models.local.Artist2
import com.example.data.models.remote.saavn.ArtistDetailResponse
import com.example.data.remote.core.NetworkResult
import kotlinx.coroutines.flow.Flow

interface ArtistsRepository {

    fun getAll(): Flow<List<Artist2>>

    suspend fun add(artist2: Artist2): Long

    suspend fun getById(artistId: String): Artist2?

    suspend fun getArtistInfo(artistUrl: String): NetworkResult<ArtistDetailResponse>

}