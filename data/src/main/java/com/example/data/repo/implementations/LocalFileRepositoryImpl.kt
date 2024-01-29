package com.example.data.repo.implementations

import com.example.data.local.datasource.LocalFileDataSource
import com.example.data.models.local.LocalFile
import com.example.data.repo.contracts.LocalFileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalFileRepositoryImpl @Inject constructor(
    private val localDataSource: LocalFileDataSource
) : LocalFileRepository {

    override suspend fun getAll(): List<LocalFile> {
        return localDataSource.getAll()
    }

    override fun getAllFlow(): Flow<List<LocalFile>> {
        return localDataSource.getAllFlow()
    }


}