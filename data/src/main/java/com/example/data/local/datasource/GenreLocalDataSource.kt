package com.example.data.local.datasource

import com.example.data.local.dao.GenreDao
import com.example.data.models.local.Genre
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenreLocalDataSource @Inject constructor(
    private val dao: GenreDao
) {

    suspend fun add(genre: Genre): Long {
        return dao.add(genre = genre)
    }

    fun getAll(): Flow<List<Genre>> {
        return dao.getAllAsFlow()
    }

}