package com.example.data.models.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.models.remote.saavn.Bio
import com.example.data.models.remote.saavn.Image

@Entity(tableName = "artists")
data class Artist2(
    @PrimaryKey
    val id: String,
    @Embedded
    val bio: Bio,
    val dob: String,
    val dominantLanguage: String? = "",
    val dominantType: String? = "",
    val fanCount: String,
    val fb: String,
    val followerCount: String,
    @Embedded
    val image: Image,
    val isRadioPresent: Boolean,
    val isVerified: Boolean,
    val name: String,
    val twitter: String,
    val url: String,
    val wiki: String
)