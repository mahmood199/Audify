package com.example.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.models.local.DownloadItem

@Dao
interface DownloadDao {

    @Query("Select * from downloads order by start_time DESC LIMIT :pageSize OFFSET :offset")
    suspend fun getAllFiles(pageSize: Int, offset: Int): List<DownloadItem>

    @Query("Select * from downloads order by start_time DESC")
    fun pagingSource(): PagingSource<Int, DownloadItem>

    @Query("SELECT * FROM downloads WHERE file_url = :fileUrl")
    suspend fun getDownloadItem(fileUrl: String): DownloadItem?

    @Query("UPDATE downloads SET download_progress = :progress WHERE file_name = :fileName")
    suspend fun updateDownload(fileName: String, progress: Double): Int

    @Insert
    suspend fun insert(item: DownloadItem): Long

    @Query("DELETE FROM downloads")
    suspend fun deleteAllRows()

}