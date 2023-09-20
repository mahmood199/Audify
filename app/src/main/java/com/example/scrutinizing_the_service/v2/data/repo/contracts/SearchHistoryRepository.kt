package com.example.scrutinizing_the_service.v2.data.repo.contracts

import com.example.scrutinizing_the_service.v2.data.models.local.RecentSearch
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {

    suspend fun getAll1(): Flow<List<RecentSearch>>

    suspend fun getAll2(): List<RecentSearch>

    suspend fun getByText(word: String): List<RecentSearch>

    suspend fun insert(recentSearch: RecentSearch): Long

    suspend fun deleteById(id: Int): Int

    suspend fun delete(recentSearch: RecentSearch): Int

}