package com.example.scrutinizing_the_service.v2.data.local.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.scrutinizing_the_service.v2.data.local.dao.ArtistDao
import com.example.scrutinizing_the_service.v2.data.local.dao.GenreDao
import com.example.scrutinizing_the_service.v2.data.local.dao.RecentSearchesDao
import com.example.scrutinizing_the_service.v2.data.local.dao.RecentlyPlayedDao
import com.example.scrutinizing_the_service.v2.data.models.local.Artist2
import com.example.scrutinizing_the_service.v2.data.models.local.Genre
import com.example.scrutinizing_the_service.v2.data.models.local.RecentSearch
import com.example.scrutinizing_the_service.v2.data.models.local.RecentlyPlayed

@Database(
    entities = [
        RecentSearch::class,
        RecentlyPlayed::class,
        Genre::class,
        Artist2::class
    ],
    version = 5,
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun recentSearchesDao(): RecentSearchesDao

    abstract fun recentlyPlayedDao(): RecentlyPlayedDao

    abstract fun genreDao(): GenreDao

    abstract fun artistsDao(): ArtistDao

}