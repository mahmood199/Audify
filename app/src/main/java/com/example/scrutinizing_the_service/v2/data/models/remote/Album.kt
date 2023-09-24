package com.example.scrutinizing_the_service.v2.data.models.remote

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("@attr")
    val rank: Rank = Rank(),
    @SerializedName("artist")
    val artist: String = "",
    @SerializedName("image")
    val images: List<Image> = listOf(),
    @SerializedName("mbid")
    val mbid: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("streamable")
    val streamable: String = "",
)