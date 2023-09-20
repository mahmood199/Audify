package com.example.scrutinizing_the_service.v2.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.scrutinizing_the_service.v2.data.local.db.RecentSearchesDao
import com.example.scrutinizing_the_service.v2.data.models.local.RecentSearch


@Database(entities = [RecentSearch::class], version = 1)
abstract class ApplicationDatabase: RoomDatabase() {

    abstract fun recentSearchesDao(): RecentSearchesDao

}