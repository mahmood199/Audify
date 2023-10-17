package com.example.scrutinizing_the_service.v2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.scrutinizing_the_service.v2.data.models.local.Genre
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(genre: Genre): Long

    @Query("SELECT * from genres")
    fun getAll(): Flow<List<Genre>>

}