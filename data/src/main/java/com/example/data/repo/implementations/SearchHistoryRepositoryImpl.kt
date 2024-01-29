package com.example.data.repo.implementations

import com.example.data.local.datasource.RecentSearchLocalDataSource
import com.example.data.models.local.RecentSearch
import com.example.data.repo.contracts.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val localDataSource: RecentSearchLocalDataSource
) : SearchHistoryRepository {

    override fun getAll1(): Flow<List<RecentSearch>> {
        return localDataSource.getAll1()
    }

    override suspend fun getAll2(): List<RecentSearch> {
        return localDataSource.getAll2()
    }

    override suspend fun getByText(word: String): List<RecentSearch> {
        return localDataSource.getByText(word = word)
    }

    override suspend fun insert(recentSearch: RecentSearch): Long {
        return localDataSource.insert(recentSearch = recentSearch)
    }

    override suspend fun deleteById(id: Int): Int {
        return localDataSource.deleteById(id = id)
    }

    override suspend fun delete(recentSearch: RecentSearch): Int {
        return localDataSource.delete(recentSearch = recentSearch)
    }

}