package com.example.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.local.datasource.FileDownloadDataSource
import com.example.data.models.local.DownloadItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FileDownloadRepository @Inject constructor(
    private val fileDownloadDataSource: FileDownloadDataSource
) {

    fun observePagingSource(): Flow<PagingData<DownloadItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10, initialLoadSize = 20),
            pagingSourceFactory = { fileDownloadDataSource.pagingSource() }
        ).flow
    }

    suspend fun getItems(): List<DownloadItem> {
        return fileDownloadDataSource.getItems(0, 0)
    }

    suspend fun clearData() {
        return fileDownloadDataSource.clearData()
    }


}