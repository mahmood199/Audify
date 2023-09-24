package com.example.scrutinizing_the_service.v2.data.models.remote

import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("@attr")
    val rank: Rank,
    @SerializedName("artist")
    val artist: String,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("image")
    val image: List<Image>,
    @SerializedName("listeners")
    val listeners: String,
    @SerializedName("mbid")
    val mbid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("streamable")
    val streamable: String,
    @SerializedName("url")
    val url: String,
)