package com.example.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName

data class Tags(
    @SerializedName("tags")
    val tag: List<Tag> = listOf(),
)