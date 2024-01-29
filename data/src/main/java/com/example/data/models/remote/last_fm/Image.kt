package com.example.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("#text")
    val text: String,
    @SerializedName("size")
    val size: String
)