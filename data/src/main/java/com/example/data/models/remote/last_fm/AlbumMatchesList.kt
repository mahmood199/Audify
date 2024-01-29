package com.example.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName

data class AlbumMatchesList(
    @SerializedName("album")
    val albums: List<Album>
)