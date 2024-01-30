package com.example.data.local.datasource

import androidx.paging.PagingSource
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

    fun pagingSource(): PagingSource<Int, RecentlyPlayed> {
        return dao.pagingSource()
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

    suspend fun updateFavorite(id: String, isFavorite: Boolean): Int {
        return dao.updateFavorite(id, isFavorite)
    }

}