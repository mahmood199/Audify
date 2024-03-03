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
) {
    companion object {
        fun default(index: Int): Artist2 {
            return Artist2(
                id = "$index",
                bio = Bio.default(),
                dob = "August 22, 1995",
                dominantLanguage = "English",
                dominantType = "Vocal",
                fanCount = "25M",
                fb = "https://www.facebook.com/dualipa/",
                followerCount = "43.7M",
                image = Image.default(),
                isRadioPresent = true,
                isVerified = true,
                name = "Dua Lipa",
                twitter = "https://twitter.com/DUALIPA",
                url = "https://www.dualipa.com/",
                wiki = "https://en.wikipedia.org/wiki/Dua_Lipa"
            )
        }
    }
}