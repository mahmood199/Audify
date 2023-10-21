package com.example.scrutinizing_the_service.v2.data.mapper

import com.example.scrutinizing_the_service.v2.data.models.local.RecentlyPlayed
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Album
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.DownloadDetail
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Image
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Song
import javax.inject.Inject

class SongMapper @Inject constructor() : Mapper<Song, RecentlyPlayed> {

    override fun map(from: Song): RecentlyPlayed {
        return from.run {
            RecentlyPlayed(
                albumId = album.id,
                albumName = album.name,
                albumUrl = album.url,
                downloadUrl = downloadUrl.last().link,
                downloadQuality = downloadUrl.last().quality,
                duration = duration,
                explicitContent = explicitContent,
                hasLyrics = true.toString() == hasLyrics,
                id = id,
                imageUrl = from.image.last().link,
                imageQuality = from.image.last().quality,
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
                downloadUrl = listOf(DownloadDetail(link = downloadUrl, quality = downloadQuality)),
                explicitContent = explicitContent,
                hasLyrics = hasLyrics.toString(),
                id = id,
                image = listOf(Image(imageUrl, imageQuality)),
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