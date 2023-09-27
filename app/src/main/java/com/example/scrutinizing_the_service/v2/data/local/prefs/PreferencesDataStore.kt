package com.example.scrutinizing_the_service.v2.data.local.prefs

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.scrutinizing_the_service.v2.data.models.local.SearchPreference
import com.example.scrutinizing_the_service.v2.data.models.local.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class PreferencesDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e("PreferencesDataStore", exception.toString())
                emit(emptyPreferences())
            } else {
                throw Exception()
            }
        }
        .map { preferences ->
            val searchPreferenceNullable = preferences[PreferencesKeys.SEARCH_TYPE_PREFERENCES]
            val searchPreference = searchPreferenceNullable ?: SearchPreference.Album.name
            val value = when (searchPreference) {
                SearchPreference.Album.name -> SearchPreference.Album
                SearchPreference.Artist.name -> SearchPreference.Artist
                else -> SearchPreference.Track
            }
            UserPreferences(searchPreference = value)
        }

    suspend fun setSearchPreferencePage(searchPreference: SearchPreference) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SEARCH_TYPE_PREFERENCES] = searchPreference.name
        }
    }

    private object PreferencesKeys {
        val SEARCH_TYPE_PREFERENCES = stringPreferencesKey("search_type")
    }

    companion object {
        const val USER_PREFERENCES_NAME = "user_preferences"
    }

}