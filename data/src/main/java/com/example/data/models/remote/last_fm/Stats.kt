package com.example.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("listeners")
    val listeners: String = "",
    @SerializedName("playcount")
    val playCount: String = "",
)