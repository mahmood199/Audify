package com.example.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Rank(
    @SerializedName("rank")
    val rank: String = "1"
)