package com.example.scrutinizing_the_service.v2.data.mapper

import com.example.scrutinizing_the_service.v2.data.models.local.RecentlyPlayed
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Album
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Song
import javax.inject.Inject

class SongMapper @Inject constructor() : Mapper<Song, RecentlyPlayed> {

    override fun map(from: Song): RecentlyPlayed {
        return from.run {
            RecentlyPlayed(
                albumId = album.id,
                albumName = album.name,
                albumUrl = album.url,
                duration = duration,
                explicitContent = explicitContent,
                hasLyrics = true.toString() == hasLyrics,
                id = id,
                label = label,
                language = language,
                name = name,
                playCount = playCount,
                releaseDate = releaseDate ?: "",
                type = type,
                url = url,
                year = year
            )
        }
    }

    override fun mapBack(from: RecentlyPlayed): Song {
        return from.run {
            Song(
                album = Album(id = albumId, name = albumName, url = albumUrl),
                duration = duration,
                explicitContent = explicitContent,
                hasLyrics = hasLyrics.toString(),
                id = id,
                label = label,
                language = language,
                name = name,
                playCount = playCount,
                releaseDate = releaseDate,
                type = type,
                url = url,
                year = year
            )
        }
    }

}