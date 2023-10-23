package com.example.scrutinizing_the_service.v2.data.repo.contracts

import com.example.scrutinizing_the_service.v2.data.models.local.Genre
import kotlinx.coroutines.flow.Flow

interface GenreRepository {

    suspend fun add(genre: Genre): Long

    fun getAll(): Flow<List<Genre>>

}