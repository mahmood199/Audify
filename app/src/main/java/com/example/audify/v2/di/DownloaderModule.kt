package com.example.audify.v2.di

import android.content.Context
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig

object DownloaderModule {

    fun providePRDownloader(
        context: Context,
    ) {
        val config = PRDownloaderConfig.newBuilder()
            .setDatabaseEnabled(true)
            .build()
        PRDownloader.initialize(context, config)
    }

}