package com.example.scrutinizing_the_service.v2.local

import android.content.Context
import androidx.room.Room
import com.example.scrutinizing_the_service.v2.data.local.db.RecentSearchesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    companion object {
        const val DB_NAME = "Music Player Application"
    }

    @Singleton
    @Provides
    fun provideRoomDataBase(
        @ApplicationContext context: Context
    ): ApplicationDatabase {
        return Room.databaseBuilder(
            context,
            ApplicationDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideRecentSearchesDao(db: ApplicationDatabase): RecentSearchesDao {
        return db.recentSearchesDao()
    }

}