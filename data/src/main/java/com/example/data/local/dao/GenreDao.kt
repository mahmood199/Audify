package com.example.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.models.local.Genre
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(genre: Genre): Long

    @Delete
    suspend fun delete(genre: Genre): Int

    @Query("Delete from genres")
    suspend fun deleteAll(): Int

    @Query("SELECT * from genres")
    fun getAllAsFlow(): Flow<List<Genre>>

    @Query("SELECT * from genres")
    fun getAllAsLiveData(): LiveData<List<Genre>>

    @Query("SELECT * From genres where id = :id")
    suspend fun getById(id: Int): Genre?


}