package com.example.scrutinizing_the_service.v2.data.models.response

import android.os.Parcelable
import com.example.scrutinizing_the_service.v2.data.models.response.Link
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Links(
    @SerializedName("link")
    val link: Link = Link(),
) : Parcelable