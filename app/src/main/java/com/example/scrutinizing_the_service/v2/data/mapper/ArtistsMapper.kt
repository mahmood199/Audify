package com.example.scrutinizing_the_service.v2.data.mapper

import com.example.scrutinizing_the_service.v2.data.models.local.Artist2
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.ArtistData
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Bio
import javax.inject.Inject

class ArtistsMapper @Inject constructor(): Mapper<ArtistData, Artist2> {

    override fun map(from: ArtistData): Artist2 {
        return from.run {
            Artist2(
                id = id,
                bio = if(bio.isEmpty()) Bio() else bio.first(),
                dob = dob,
                dominantLanguage = dominantLanguage,
                dominantType = dominantType,
                fanCount = fanCount,
                fb = fb,
                followerCount = followerCount,
                image = image.last(),
                isRadioPresent = isRadioPresent,
                isVerified = isVerified,
                name = name,
                twitter = twitter,
                url = url,
                wiki = wiki
            )
        }
    }

    override fun mapBack(from: Artist2): ArtistData {
        return from.run {
            ArtistData(
                bio = listOf(bio),
                dob = dob,
                dominantLanguage = dominantLanguage,
                dominantType = dominantType,
                fanCount = fanCount,
                fb = fb,
                followerCount = followerCount,
                id = id,
                image = listOf(image),
                isRadioPresent = isRadioPresent,
                isVerified = isVerified,
                name = name,
                twitter = twitter,
                url = url,
                wiki = wiki
            )
        }
    }

}