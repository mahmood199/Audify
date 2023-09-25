package com.example.scrutinizing_the_service.v2.data.models.remote

import com.google.gson.annotations.SerializedName

data class Bio(
    @SerializedName("content")
    val content: String = "",
    @SerializedName("links")
    val links: Links = Links(),
    @SerializedName("summary")
    val summary: String = ""
)