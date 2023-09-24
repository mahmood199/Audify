package com.example.scrutinizing_the_service.v2.data.models.remote

import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("listeners")
    val listeners: String = "",
    @SerializedName("playcount")
    val playCount: String = "",
)