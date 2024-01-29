package com.example.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName

data class Similar(
    @SerializedName("artist")
    val artist: List<Artist> = listOf(),
)