package com.example.scrutinizing_the_service.v2.data.models.remote.saavn

data class ArtistData(
    val availableLanguages: List<String> = emptyList(),
    val bio: List<Bio>,
    val dob: String,
    val dominantLanguage: String? = null,
    val dominantType: String? = null,
    val fanCount: String,
    val fb: String,
    val followerCount: String,
    val id: String,
    val image: List<Image>,
    val isRadioPresent: Boolean,
    val isVerified: Boolean,
    val name: String,
    val twitter: String,
    val url: String,
    val wiki: String
)