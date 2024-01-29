package com.example.data.local.datasource

import com.example.data.local.dao.RecentSearchesDao
import com.example.data.models.local.RecentSearch
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
        val item = getByText(recentSearch.query)
        return if(item.isEmpty()) {
            dao.insert(recentSearch)
        } else {
            -2
        }
    }

    suspend fun deleteById(id: Int): Int {
        return dao.deleteById(id)
    }

    suspend fun delete(recentSearch: RecentSearch): Int {
        return dao.delete(recentSearch)
    }

}