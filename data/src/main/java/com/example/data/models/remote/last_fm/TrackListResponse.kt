package com.example.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName

data class TrackListResponse(
    @SerializedName("results")
    val results: TrackResults
)
