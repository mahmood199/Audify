package com.example.scrutinizing_the_service.v2.data.repo.di_module

import android.content.Context
import com.example.scrutinizing_the_service.v2.data.local.prefs.PreferencesDataStore
import com.example.scrutinizing_the_service.v2.data.repo.contracts.UserPreferenceRepository
import com.example.scrutinizing_the_service.v2.data.repo.implementations.UserPreferenceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideUserPreferenceRepository(
        preferencesDataStore: PreferencesDataStore,
        @ApplicationContext context: Context
    ): UserPreferenceRepository {
        return UserPreferenceRepositoryImpl(
            dataStore = preferencesDataStore,
            context = context
        )
    }

}