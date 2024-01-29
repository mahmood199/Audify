package com.example.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName

data class TrackMatchesList(
    @SerializedName("track")
    val tracks: List<Track>
)