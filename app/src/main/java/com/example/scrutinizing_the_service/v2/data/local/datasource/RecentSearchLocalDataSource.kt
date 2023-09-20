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


}