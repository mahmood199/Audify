package com.example.data.models.remote.last_fm

import com.example.data.models.remote.last_fm.Image
import com.example.data.models.remote.last_fm.Rank
import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("@attr")
    val rank: Rank? = null,
    @SerializedName("artist")
    val artist: String,
    @SerializedName("duration")
    val duration: String? = null,
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