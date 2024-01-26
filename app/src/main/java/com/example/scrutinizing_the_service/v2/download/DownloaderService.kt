package com.example.scrutinizing_the_service.v2.download

import android.app.Service
import android.content.Intent
import android.os.Binder

class DownloaderService : Service() {

    private val binder = DownloadServiceBinder()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?) = binder

    inner class DownloadServiceBinder : Binder() {
        fun getService() = this@DownloaderService
    }

}