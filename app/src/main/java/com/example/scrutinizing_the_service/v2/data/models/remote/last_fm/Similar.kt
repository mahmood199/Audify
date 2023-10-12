package com.example.scrutinizing_the_service.v2.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName

data class Similar(
    @SerializedName("artist")
    val artist: List<Artist> = listOf(),
)