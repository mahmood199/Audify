package com.example.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("#text")
    val text: String = "",
    @SerializedName("href")
    val href: String = "",
    @SerializedName("rel")
    val rel: String = "",
)