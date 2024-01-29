package com.example.data.models.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("recently_played")
data class RecentlyPlayed(
    val albumId: String,
    val albumName: String,
    val albumUrl: String,
    val duration: String,
    val downloadUrl : String,
    val downloadQuality : String,
    val explicitContent: String,
    val hasLyrics: Boolean,
    @PrimaryKey
    val id: String,
    val imageUrl: String,
    val imageQuality: String,
    val label: String,
    val language: String,
    val name: String,
    val playCount: String,
    val playCountLocal: Int = 0,
    val releaseDate: String,
    val type: String,
    @ColumnInfo("time_stamp")
    val timeStamp: Long = System.currentTimeMillis(),
    val url: String,
    val year: String
)
