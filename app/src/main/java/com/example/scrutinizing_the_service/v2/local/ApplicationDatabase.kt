package com.example.scrutinizing_the_service.v2.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.scrutinizing_the_service.v2.data.local.dao.RecentSearchesDao
import com.example.scrutinizing_the_service.v2.data.local.dao.RecentlyPlayedDao
import com.example.scrutinizing_the_service.v2.data.models.local.RecentSearch
import com.example.scrutinizing_the_service.v2.data.models.local.RecentlyPlayed


@Database(entities = [RecentSearch::class, RecentlyPlayed::class], version = 2)
abstract class ApplicationDatabase: RoomDatabase() {

    abstract fun recentSearchesDao(): RecentSearchesDao

    abstract fun recentlyPlayedDao(): RecentlyPlayedDao

}