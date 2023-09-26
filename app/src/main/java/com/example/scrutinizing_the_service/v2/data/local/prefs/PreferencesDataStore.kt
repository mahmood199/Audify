package com.example.scrutinizing_the_service.v2.data.local.prefs

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.scrutinizing_the_service.v2.data.models.local.SearchPreference
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesDataStore @Inject constructor(
    @ApplicationContext val context: Context
) {

    val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    val userPreferencesFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            val showCompleted =
                preferences[PreferencesKeys.SEARCH_TYPE_PREFERENCES] ?: SearchPreference.Track.name
            showCompleted
        }

    suspend fun updateShowCompleted(searchPreference: SearchPreference) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SEARCH_TYPE_PREFERENCES] = searchPreference.name
        }
    }
    private object PreferencesKeys {
        val SEARCH_TYPE_PREFERENCES = stringPreferencesKey("search_type")
    }

    companion object {
        private const val USER_PREFERENCES_NAME = "user_preferences"
    }

}