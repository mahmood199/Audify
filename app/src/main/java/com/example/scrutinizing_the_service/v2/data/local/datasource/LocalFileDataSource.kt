package com.example.scrutinizing_the_service.v2.data.local.datasource

import com.example.scrutinizing_the_service.v2.data.local.dao.LocalFileDao
import com.example.scrutinizing_the_service.v2.data.models.local.LocalFile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalFileDataSource @Inject constructor(
    private val fileDao: LocalFileDao
) {

    suspend fun getAll(): List<LocalFile> {
        return fileDao.getAllFiles()
    }

    fun getAllFlow(): Flow<List<LocalFile>> {
        return fileDao.getAll1()
    }

}