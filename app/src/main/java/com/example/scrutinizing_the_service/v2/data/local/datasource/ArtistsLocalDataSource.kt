package com.example.scrutinizing_the_service.v2.data.local.datasource

import com.example.scrutinizing_the_service.v2.data.local.dao.ArtistDao
import com.example.scrutinizing_the_service.v2.data.models.local.Artist2
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArtistsLocalDataSource @Inject constructor(
    val dao: ArtistDao
) {

    suspend fun add(artist2: Artist2): Long {
        return dao.add(artist2)
    }

    fun getAll(): Flow<List<Artist2>> {
        return dao.getAll()
    }

    suspend fun getById(id: String): Artist2? {
        return dao.getById(id)
    }

}