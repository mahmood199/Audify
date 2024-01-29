package com.example.data.repo.di_module

import com.example.data.local.datasource.ArtistsLocalDataSource
import com.example.data.local.datasource.GenreLocalDataSource
import com.example.data.local.datasource.LocalFileDataSource
import com.example.data.local.datasource.SongsLocalDataSource
import com.example.data.local.prefs.PreferencesDataStore
import com.example.data.mapper.ArtistsMapper
import com.example.data.mapper.SongMapper
import com.example.data.remote.saavn.ArtistsRemoteDataSource
import com.example.data.remote.saavn.SongsRemoteDataSource
import com.example.data.repo.contracts.ArtistsRepository
import com.example.data.repo.contracts.GenreRepository
import com.example.data.repo.contracts.LocalFileRepository
import com.example.data.repo.contracts.SongsRepository
import com.example.data.repo.contracts.UserPreferenceRepository
import com.example.data.repo.implementations.ArtistsRepositoryImpl
import com.example.data.repo.implementations.GenreRepositoryImpl
import com.example.data.repo.implementations.LocalFileRepositoryImpl
import com.example.data.repo.implementations.SongsRepositoryImpl
import com.example.data.repo.implementations.UserPreferenceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserPreferenceRepository(
        preferencesDataStore: PreferencesDataStore,
    ): UserPreferenceRepository {
        return UserPreferenceRepositoryImpl(
            dataStore = preferencesDataStore,
        )
    }

    @Provides
    @Singleton
    fun provideSongsRepository(
        remoteDataSource: SongsRemoteDataSource,
        localDataSource: SongsLocalDataSource,
        mapper: SongMapper
    ): SongsRepository = SongsRepositoryImpl(
        remoteDataSource = remoteDataSource,
        localDataSource = localDataSource,
        mapper = mapper
    )

    @Provides
    @Singleton
    fun provideArtistsRepository(
        localDataSource: ArtistsLocalDataSource,
        remoteDataSource: ArtistsRemoteDataSource,
        mapper: ArtistsMapper
    ): ArtistsRepository = ArtistsRepositoryImpl(
        localDataSource = localDataSource,
        remoteDataSource = remoteDataSource,
        mapper = mapper
    )

    @Provides
    @Singleton
    fun providesGenreRepository(
        localDataSource: GenreLocalDataSource
    ) : GenreRepository = GenreRepositoryImpl(localDataSource)

    @Provides
    @Singleton
    fun provideLocalFileRepository(
        localFileDataSource: LocalFileDataSource
    ): LocalFileRepository = LocalFileRepositoryImpl(localFileDataSource)

}