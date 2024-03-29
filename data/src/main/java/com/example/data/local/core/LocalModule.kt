package com.example.data.local.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.local.dao.ArtistDao
import com.example.data.local.dao.DownloadDao
import com.example.data.local.dao.GenreDao
import com.example.data.local.dao.LocalFileDao
import com.example.data.local.dao.RecentSearchesDao
import com.example.data.local.dao.RecentlyPlayedDao
import com.example.data.local.prefs.PreferencesDataStore
import com.example.data.remote.saavn.genres
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Singleton
import kotlin.coroutines.EmptyCoroutineContext

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
        )
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                val job = CoroutineScope(EmptyCoroutineContext + Dispatchers.IO)
                job.launch(Dispatchers.IO) {
                    try {
                        genres.forEachIndexed { _, s ->
                            db.execSQL("INSERT INTO genres (name, userSelected) VALUES ('$s', 'false')")
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        ).build()
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(
                SharedPreferencesMigration(
                    context,
                    PreferencesDataStore.USER_PREFERENCES_NAME
                )
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = {
                context.preferencesDataStoreFile(
                    PreferencesDataStore.USER_PREFERENCES_NAME
                )
            }
        )
    }

    @Singleton
    @Provides
    fun provideRecentSearchesDao(db: ApplicationDatabase): RecentSearchesDao {
        return db.recentSearchesDao()
    }

    @Singleton
    @Provides
    fun provideRecentlyPlayedDao(db: ApplicationDatabase): RecentlyPlayedDao {
        return db.recentlyPlayedDao()
    }

    @Singleton
    @Provides
    fun provideGenreDao(db: ApplicationDatabase): GenreDao {
        return db.genreDao()
    }

    @Singleton
    @Provides
    fun provideArtistDao(db: ApplicationDatabase): ArtistDao {
        return db.artistsDao()
    }

    @Singleton
    @Provides
    fun provideLocalFilesDao(db: ApplicationDatabase): LocalFileDao {
        return db.fileDao()
    }

    @Singleton
    @Provides
    fun provideDownloadItemDao(db: ApplicationDatabase): DownloadDao {
        return db.downloadFileDao()
    }


}