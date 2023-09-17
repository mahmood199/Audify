package com.example.scrutinizing_the_service.v2.data.models.response

import com.google.gson.annotations.SerializedName

data class TrackListResponse(
    @SerializedName("tracks")
    val tracks: Tracks
)