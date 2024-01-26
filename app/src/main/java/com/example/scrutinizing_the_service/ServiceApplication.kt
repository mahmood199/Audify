package com.example.scrutinizing_the_service

import android.app.Application
import com.example.scrutinizing_the_service.v2.di.DownloaderModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ServiceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DownloaderModule.providePRDownloader(context = this)
    }

}