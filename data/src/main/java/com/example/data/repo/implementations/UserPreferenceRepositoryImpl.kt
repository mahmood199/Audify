package com.example.data.repo.implementations

import com.example.data.local.prefs.PreferencesDataStore
import com.example.data.models.local.SearchPreference
import com.example.data.models.local.UserPreferences
import com.example.data.repo.contracts.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPreferenceRepositoryImpl @Inject constructor(
    private val dataStore: PreferencesDataStore,
) : UserPreferenceRepository {

    override fun getSearchPreference(): Flow<UserPreferences> {
        return dataStore.userPreferencesFlow
    }

    override suspend fun setSearchPreference(searchPreference: SearchPreference) {
        dataStore.setSearchPreferencePage(searchPreference)
    }

}