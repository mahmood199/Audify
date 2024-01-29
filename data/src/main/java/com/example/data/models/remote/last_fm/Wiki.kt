package com.example.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName

data class Wiki(
    @SerializedName("summary")
    val summary: String,
    @SerializedName("content")
    val content: String,
)
