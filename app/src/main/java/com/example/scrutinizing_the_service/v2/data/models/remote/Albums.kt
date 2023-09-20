package com.example.scrutinizing_the_service.v2.data.models.remote

import com.google.gson.annotations.SerializedName

data class Albums(
    @SerializedName("@attr")
    val attr: OffsetAttribute,
    @SerializedName("album")
    val album: List<Album>
)