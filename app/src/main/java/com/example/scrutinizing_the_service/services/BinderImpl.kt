package com.example.scrutinizing_the_service.services

import android.os.Binder

class BinderImpl(private val russianService: RussianService) : Binder() {

    fun getService() = russianService
}