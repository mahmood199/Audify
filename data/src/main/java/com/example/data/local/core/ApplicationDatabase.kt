package com.example.data.local.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.ArtistDao
import com.example.data.local.dao.DownloadDao
import com.example.data.local.dao.GenreDao
import com.example.data.local.dao.LocalFileDao
import com.example.data.local.dao.RecentSearchesDao
import com.example.data.local.dao.RecentlyPlayedDao
import com.example.data.models.local.Artist2
import com.example.data.models.local.DownloadItem
import com.example.data.models.local.Genre
import com.example.data.models.local.LocalFile
import com.example.data.models.local.RecentSearch
import com.example.data.models.local.RecentlyPlayed

@Database(
    entities = [
        RecentSearch::class,
        RecentlyPlayed::class,
        Genre::class,
        Artist2::class,
        LocalFile::class,
        DownloadItem::class
    ],
    version = 10,
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun recentSearchesDao(): RecentSearchesDao

    abstract fun recentlyPlayedDao(): RecentlyPlayedDao

    abstract fun genreDao(): GenreDao

    abstract fun artistsDao(): ArtistDao

    abstract fun fileDao(): LocalFileDao

    abstract fun downloadFileDao(): DownloadDao

}