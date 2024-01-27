package com.example.scrutinizing_the_service.v2.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.example.scrutinizing_the_service.v2.data.models.local.DownloadItem

@Dao
interface DownloadDao {

    @Query("Select * from downloads order by start_time DESC LIMIT :pageSize OFFSET :offset")
    suspend fun getAllFiles(pageSize: Int, offset: Int): List<DownloadItem>

    @Query("Select * from downloads order by start_time DESC")
    fun pagingSource(): PagingSource<Int, DownloadItem>

    @Query("UPDATE downloads SET download_progress = :progress AND file_size_in_bytes = :fileSize WHERE file_name = :fileName")
    suspend fun insertOrUpdateDownload(fileName: String, progress: Double, fileSize: Long)

    @Query("SELECT * FROM downloads WHERE file_url = :fileUrl")
    suspend fun getDownloadItem(fileUrl: String): DownloadItem?
}