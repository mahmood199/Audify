package com.example.data.local.datasource

import com.example.data.local.dao.LocalFileDao
import com.example.data.models.local.LocalFile
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