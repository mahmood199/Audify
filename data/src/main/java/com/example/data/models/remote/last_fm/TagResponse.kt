package com.example.data.models.remote.last_fm

import com.example.data.models.remote.last_fm.Tag
import com.google.gson.annotations.SerializedName

data class TagResponse(
    @SerializedName("tag")
    val tag: Tag,
)
