package com.example.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName

data class AlbumsResults(
    @SerializedName("@attr")
    val attr: OffsetAttribute?,
    @SerializedName("albummatches")
    val album: AlbumMatchesList
)