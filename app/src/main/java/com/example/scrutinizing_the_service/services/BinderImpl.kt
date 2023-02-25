package com.example.scrutinizing_the_service.services

import android.os.Binder

class BinderImpl(private val services: SmartServices) : Binder() {

    fun getService() = services

}