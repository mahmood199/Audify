package com.example.scrutinizing_the_service.v2.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.scrutinizing_the_service.v2.data.models.local.RecentSearch
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchesDao {

    @Query("Select * from recent_searches order by time_stamp DESC")
    fun getAll1() : Flow<List<RecentSearch>>

    @Query("Select * from recent_searches")
    suspend fun getAll2() : List<RecentSearch>

    @Query("Select * from recent_searches where search Like '%' || :word  || '%'")
    suspend fun getByText(word: String): List<RecentSearch>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recentSearch: RecentSearch): Long

    @Query("Delete from recent_searches where id=:id")
    suspend fun deleteById(id: Int): Int

    @Delete
    suspend fun delete(recentSearch: RecentSearch): Int

}