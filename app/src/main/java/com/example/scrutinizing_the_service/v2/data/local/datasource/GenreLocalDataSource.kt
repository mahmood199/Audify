package com.example.scrutinizing_the_service.v2.data.local.datasource

import com.example.scrutinizing_the_service.v2.data.local.dao.GenreDao
import com.example.scrutinizing_the_service.v2.data.models.local.Genre
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenreLocalDataSource @Inject constructor(
    private val dao: GenreDao
) {

    suspend fun add(genre: Genre): Long {
        return dao.add(genre = genre)
    }

    fun getAll(): Flow<List<Genre>> {
        return dao.getAll()
    }

}