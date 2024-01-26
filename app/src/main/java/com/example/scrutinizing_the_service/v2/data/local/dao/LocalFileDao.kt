package com.example.scrutinizing_the_service.v2.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.scrutinizing_the_service.v2.data.models.local.LocalFile
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalFileDao {

    @Query("Select * from localfile order by time_stamp DESC")
    suspend fun getAllFiles(): List<LocalFile>

    @Query("Select * from localfile order by time_stamp DESC")
    fun getAll1(): Flow<List<LocalFile>>

}