package com.example.scrutinizing_the_service.v2.data.models.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rank(
    @SerializedName("rank")
    val rank: String = "1"
) : Parcelable