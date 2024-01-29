package com.example.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName

data class TrackResults(
    @SerializedName("@attr")
    val attr: OffsetAttribute?,
    @SerializedName("trackmatches")
    val trackMatchesList: TrackMatchesList
)