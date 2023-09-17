package com.example.scrutinizing_the_service.v2.data.models.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Track(
    @SerializedName("@attr")
    val rank: Rank,
    @SerializedName("artist")
    val artist: Artist,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("image")
    val image: List<Image>,
    @SerializedName("mbid")
    val mbid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("streamable")
    val streamable: Streamable,
    @SerializedName("url")
    val url: String,
) : Parcelable