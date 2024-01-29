package com.example.data.repo.contracts

import com.example.data.models.local.SearchPreference
import com.example.data.models.local.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {

    fun getSearchPreference(): Flow<UserPreferences>

    suspend fun setSearchPreference(searchPreference: SearchPreference)

}