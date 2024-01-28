package com.example.scrutinizing_the_service.v2.data.models.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("downloads")
data class DownloadItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "file_name")
    val fileName: String,
    @ColumnInfo(name = "file_url")
    val fileUrl: String,
    @ColumnInfo(name = "file_location")
    val fileLocation: String,
    @ColumnInfo(name = "download_progress")
    val downloadProgress: Double = 0.0,
    val isDownloadedAnimationShown: Boolean = false,
    @ColumnInfo(name = "file_size_in_bytes")
    val fileSizeInBytes: Long,
    @ColumnInfo(name = "start_time")
    val startTime: Long,
    @ColumnInfo(name = "end_time")
    val endTime: Long,
    @ColumnInfo(name = "error_message")
    val errorMessage: String? = null
) {
    companion object {
        fun default(): DownloadItem {
            return DownloadItem(-1,
                "Sample file",
                "https://onlinetestcase.com/wp-content/uploads/2023/06/1-MB-MP3.mp3",
                "Sample file location",
                50.0,
                false,
                30000,
                37427674L,
                75767543L,
                ""
            )
        }
    }
}