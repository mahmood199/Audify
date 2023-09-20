package com.example.scrutinizing_the_service.v2.data.local.datasource

import com.example.scrutinizing_the_service.v2.data.local.db.RecentSearchesDao
import com.example.scrutinizing_the_service.v2.data.models.local.RecentSearch
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecentSearchLocalDataSource @Inject constructor(
    private val dao: RecentSearchesDao
) {

    fun getAll1(): Flow<List<RecentSearch>> {
        return dao.getAll1()
    }

    suspend fun getAll2(): List<RecentSearch> {
        return dao.getAll2()
    }

    suspend fun getByText(word: String): List<RecentSearch> {
        return dao.getByText(word)
    }

    suspend fun insert(recentSearch: RecentSearch): Long {
        return dao.insert(recentSearch)
    }

    suspend fun deleteById(id: Int): Int {
        return dao.deleteById(id)
    }

    suspend fun delete(recentSearch: RecentSearch): Int {
        return dao.delete(recentSearch)
    }

}