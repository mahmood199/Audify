package com.download.service.services

import android.os.Binder
import com.download.service.services.SmartServices

class BinderImpl(private val services: SmartServices) : Binder() {

    fun getService() = services

}