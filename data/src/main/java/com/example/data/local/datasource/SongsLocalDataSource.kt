package com.example.data.local.datasource

import com.example.data.local.dao.RecentlyPlayedDao
import com.example.data.models.local.RecentlyPlayed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class SongsLocalDataSource @Inject constructor(
    private val dao: RecentlyPlayedDao
) {

    fun sortByMostRecentlyPlayed(): Flow<List<RecentlyPlayed>> {
        return dao.sortByMostRecentlyPlayed().distinctUntilChanged()
    }

    fun sortByMostPlayed(): Flow<List<RecentlyPlayed>> {
        return dao.sortByMostPlayed().distinctUntilChanged()
    }

    suspend fun getAll(): List<RecentlyPlayed> {
        return dao.getAll()
    }

    suspend fun insertSongs(songs: List<RecentlyPlayed>) {
        songs.forEachIndexed { _, item ->
            dao.upsert(item)
        }
    }

}