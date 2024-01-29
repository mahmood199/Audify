package com.example.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName

data class ArtistResults(
    @SerializedName("@attr")
    val attr: OffsetAttribute?,
    @SerializedName("artistmatches")
    val artistMatchesList: ArtistMatchesList
)