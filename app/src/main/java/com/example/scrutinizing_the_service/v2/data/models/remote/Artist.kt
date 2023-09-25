package com.example.scrutinizing_the_service.v2.data.models.remote

import com.google.gson.annotations.SerializedName

data class Artist(
    @SerializedName("mbid")
    val mbid: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("@attr")
    val rank: Rank = Rank(),
    @SerializedName("image")
    val image: List<Image> = listOf(),
    @SerializedName("streamable")
    val streamable: String = "",
    @SerializedName("listeners")
    val listeners: String,
    @SerializedName("bio")
    val bio: Bio = Bio(),
    @SerializedName("ontour")
    val onTour: String = "",
    @SerializedName("similar")
    val similar: Similar = Similar(),
    @SerializedName("stats")
    val stats: Stats = Stats(),
    @SerializedName("tags")
    val tags: Tags = Tags(),
)