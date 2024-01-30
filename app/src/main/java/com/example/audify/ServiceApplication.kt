package com.example.audify

import android.app.Application
import com.example.audify.v2.di.DownloaderModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ServiceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DownloaderModule.providePRDownloader(context = this)
    }

}