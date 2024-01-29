package com.example.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("link")
    val link: Link = Link(),
)