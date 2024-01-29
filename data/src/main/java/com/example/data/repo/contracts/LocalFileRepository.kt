package com.example.data.repo.contracts

import com.example.data.models.local.LocalFile
import kotlinx.coroutines.flow.Flow

interface LocalFileRepository {

    suspend fun getAll(): List<LocalFile>

    fun getAllFlow(): Flow<List<LocalFile>>

}