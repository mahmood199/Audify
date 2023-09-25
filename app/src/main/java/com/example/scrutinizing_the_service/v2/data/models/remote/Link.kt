package com.example.scrutinizing_the_service.v2.data.models.remote

import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("#text")
    val text: String = "",
    @SerializedName("href")
    val href: String = "",
    @SerializedName("rel")
    val rel: String = "",
)