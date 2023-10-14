package com.example.scrutinizing_the_service.v2.data.local.datasource

import com.example.scrutinizing_the_service.v2.data.local.dao.RecentlyPlayedDao
import com.example.scrutinizing_the_service.v2.data.models.local.RecentlyPlayed
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Song
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SongsLocalDataSource @Inject constructor(
    private val dao: RecentlyPlayedDao
) {

    fun sortByMostRecentlyPlayed(): Flow<List<RecentlyPlayed>> {
        return dao.sortByMostPlayed()
    }

    suspend fun insertSongs(songs: List<RecentlyPlayed>) {
        songs.forEachIndexed { _, item ->
            dao.upsert(item)
        }
    }

}