package com.example.data.local.datasource

import android.util.Log
import androidx.paging.PagingSource
import com.example.data.local.dao.DownloadDao
import com.example.data.models.local.DownloadItem
import com.example.data.models.local.RecentlyPlayed
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

    suspend fun updateDownloadState(
        recentlyPlayed: RecentlyPlayed,
        progress: Double,
        sizeInBytes: Long
    ) {
        val item = dao.getDownloadItem(recentlyPlayed.downloadUrl)
        if (item == null) {
            val downloadItem = DownloadItem(
                fileName = recentlyPlayed.name,
                fileUrl = recentlyPlayed.downloadUrl,
                fileLocation = "",
                downloadProgress = 0.0,
                fileSizeInBytes = sizeInBytes,
                startTime = System.currentTimeMillis(),
                endTime = System.currentTimeMillis()
            )
            val rows = dao.insert(item = downloadItem)
            Log.d("FileDataSource", "InsertFile --> Affected rows $rows")
        } else {
            val rows = dao.updateDownload(
                fileName = recentlyPlayed.name,
                progress = progress,
            )
            Log.d("FileDataSource", "UpdateFile --> Affected rows $rows")
        }

        val result = dao.getDownloadItem(fileUrl = recentlyPlayed.downloadUrl)
        Log.d("FileDataSource", "Actual progress $progress")
        Log.d("FileDataSource", "Row progress ${result?.downloadProgress}")
    }

    suspend fun clearData() {
        return dao.deleteAllRows()
    }

}