package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.models.local.Artist2
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {

    @Query("SELECT * FROM artists")
    fun getAllAsFlow(): Flow<List<Artist2>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(artist2: Artist2): Long

    @Query("SELECT * from artists WHERE id = :artistId")
    suspend fun getById(artistId: String): Artist2?

    @Query("SELECT * from artists")
    suspend fun getAll(): List<Artist2>


}