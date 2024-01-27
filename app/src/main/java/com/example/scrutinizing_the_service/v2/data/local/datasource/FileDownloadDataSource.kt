package com.example.scrutinizing_the_service.v2.data.local.datasource

import androidx.paging.PagingSource
import com.example.scrutinizing_the_service.v2.data.local.dao.DownloadDao
import com.example.scrutinizing_the_service.v2.data.models.local.DownloadItem
import javax.inject.Inject

class FileDownloadDataSource @Inject constructor(
    private val dao: DownloadDao
) {

    fun pagingSource(): PagingSource<Int, DownloadItem> {
        return dao.pagingSource()
    }

    suspend fun getItems(pageSize: Int, offset: Int): List<DownloadItem> {
        return dao.getAllFiles(pageSize, offset)
    }

    suspend fun updateDownloadState(fileName: String, progress: Double, sizeInBytes: Long) {
        dao.insertOrUpdateDownload(fileName, progress, sizeInBytes)
    }

}