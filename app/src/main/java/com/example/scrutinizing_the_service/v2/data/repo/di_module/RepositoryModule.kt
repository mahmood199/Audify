package com.example.scrutinizing_the_service.v2.data.repo.di_module

import com.example.scrutinizing_the_service.v2.data.local.datasource.ArtistsLocalDataSource
import com.example.scrutinizing_the_service.v2.data.local.datasource.GenreLocalDataSource
import com.example.scrutinizing_the_service.v2.data.local.datasource.LocalFileDataSource
import com.example.scrutinizing_the_service.v2.data.local.datasource.SongsLocalDataSource
import com.example.scrutinizing_the_service.v2.data.local.prefs.PreferencesDataStore
import com.example.scrutinizing_the_service.v2.data.mapper.ArtistsMapper
import com.example.scrutinizing_the_service.v2.data.mapper.SongMapper
import com.example.scrutinizing_the_service.v2.data.remote.saavn.ArtistsRemoteDataSource
import com.example.scrutinizing_the_service.v2.data.remote.saavn.SongsRemoteDataSource
import com.example.scrutinizing_the_service.v2.data.repo.contracts.ArtistsRepository
import com.example.scrutinizing_the_service.v2.data.repo.contracts.GenreRepository
import com.example.scrutinizing_the_service.v2.data.repo.contracts.LocalFileRepository
import com.example.scrutinizing_the_service.v2.data.repo.contracts.SongsRepository
import com.example.scrutinizing_the_service.v2.data.repo.contracts.UserPreferenceRepository
import com.example.scrutinizing_the_service.v2.data.repo.implementations.ArtistsRepositoryImpl
import com.example.scrutinizing_the_service.v2.data.repo.implementations.GenreRepositoryImpl
import com.example.scrutinizing_the_service.v2.data.repo.implementations.LocalFileRepositoryImpl
import com.example.scrutinizing_the_service.v2.data.repo.implementations.SongsRepositoryImpl
import com.example.scrutinizing_the_service.v2.data.repo.implementations.UserPreferenceRepositoryImpl
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