package com.example.scrutinizing_the_service.v2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.scrutinizing_the_service.v2.data.models.local.RecentSearch
import com.example.scrutinizing_the_service.v2.data.models.local.RecentlyPlayed
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentlyPlayedDao {

    @Query("Select * from recently_played order by time_stamp DESC")
    fun sortByMostRecentlyPlayed(): Flow<List<RecentlyPlayed>>

    @Query("Select * from recently_played order by playCount DESC")
    fun sortByMostPlayed(): Flow<List<RecentlyPlayed>>

    @Query(
        "Select * from recently_played where " +
                "name Like '%' || :word  || '%' or " +
                "albumName Like '%' || :word  || '%'"
    )
    suspend fun getByText(word: String): List<RecentlyPlayed>

    @Query("SELECT * from recently_played")
    suspend fun getAll(): List<RecentlyPlayed>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: RecentlyPlayed): Long


    @Query("Delete from recently_played where id=:id")
    suspend fun deleteById(id: Int): Int

    @Delete
    suspend fun delete(recentSearch: RecentlyPlayed): Int


}