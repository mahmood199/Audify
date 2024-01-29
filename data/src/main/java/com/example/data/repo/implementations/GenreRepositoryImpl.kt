package com.example.data.repo.implementations

import com.example.data.local.datasource.GenreLocalDataSource
import com.example.data.models.local.Genre
import com.example.data.repo.contracts.GenreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val localDataSource: GenreLocalDataSource
): GenreRepository {

    override suspend fun add(genre: Genre): Long {
        return localDataSource.add(genre = genre)
    }

    override fun getAll(): Flow<List<Genre>> {
        return localDataSource.getAll()
    }

}