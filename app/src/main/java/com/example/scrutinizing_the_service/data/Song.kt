package com.example.scrutinizing_the_service.data

data class Song(
    val name: String,
    val artist: String,
    val isFavourite: Boolean,
    val path : String = ""
)