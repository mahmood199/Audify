package com.example.scrutinizing_the_service.v2.data.models.remote

import com.google.gson.annotations.SerializedName

data class TrackResults(
    @SerializedName("@attr")
    val attr: OffsetAttribute?,
    @SerializedName("trackmatches")
    val trackMatchesList: TrackMatchesList
)