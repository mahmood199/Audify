package com.example.scrutinizing_the_service.v2.data.repo.implementations

import android.content.Context
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.scrutinizing_the_service.v2.data.local.prefs.PreferencesDataStore
import com.example.scrutinizing_the_service.v2.data.models.local.SearchPreference
import com.example.scrutinizing_the_service.v2.data.repo.contracts.UserPreferenceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPreferenceRepositoryImpl @Inject constructor(
    private val dataStore: PreferencesDataStore,
    @ApplicationContext val context: Context
) : UserPreferenceRepository {

    override fun getSearchPreference(): Flow<String> {
        return dataStore.userPreferencesFlow
    }

    override suspend fun setSearchPreference(searchPreference: SearchPreference) {
        dataStore.updateShowCompleted(searchPreference)
    }

}