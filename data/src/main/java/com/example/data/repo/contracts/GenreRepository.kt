package com.example.data.repo.contracts

import com.example.data.models.local.Genre
import kotlinx.coroutines.flow.Flow

interface GenreRepository {

    suspend fun add(genre: Genre): Long

    fun getAll(): Flow<List<Genre>>

}