package com.example.scrutinizing_the_service.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val name: String,
    val artist: String,
    val isFavourite: Boolean = false,
    val path: String = "",
    val duration: Int = 0
) : Parcelable