package com.example.scrutinizing_the_service.v2.data.repo.implementations

import com.example.scrutinizing_the_service.v2.data.local.prefs.PreferencesDataStore
import com.example.scrutinizing_the_service.v2.data.models.local.SearchPreference
import com.example.scrutinizing_the_service.v2.data.models.local.UserPreferences
import com.example.scrutinizing_the_service.v2.data.repo.contracts.UserPreferenceRepository
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