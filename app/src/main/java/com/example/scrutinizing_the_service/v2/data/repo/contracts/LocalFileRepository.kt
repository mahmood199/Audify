package com.example.scrutinizing_the_service.v2.data.repo.contracts

import com.example.scrutinizing_the_service.v2.data.models.local.LocalFile
import kotlinx.coroutines.flow.Flow

interface LocalFileRepository {

    suspend fun getAll(): List<LocalFile>

    fun getAllFlow(): Flow<List<LocalFile>>

}