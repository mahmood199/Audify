package com.example.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName

data class Bio(
    @SerializedName("content")
    val content: String = "",
    @SerializedName("links")
    val links: Links = Links(),
    @SerializedName("summary")
    val summary: String = ""
)