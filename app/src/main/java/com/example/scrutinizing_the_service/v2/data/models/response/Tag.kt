package com.example.scrutinizing_the_service.v2.data.models.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tag(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("name")
    var name: String,
    @SerializedName("reach")
    val reach: Int = 0,
    @SerializedName("wiki")
    val wiki: Wiki? = null,
    @SerializedName("url")
    val url: String? = ""
) : Parcelable