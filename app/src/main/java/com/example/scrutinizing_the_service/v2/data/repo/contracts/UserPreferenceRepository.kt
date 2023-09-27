package com.example.scrutinizing_the_service.v2.data.repo.contracts

import com.example.scrutinizing_the_service.v2.data.models.local.SearchPreference
import com.example.scrutinizing_the_service.v2.data.models.local.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {

    fun getSearchPreference(): Flow<UserPreferences>

    suspend fun setSearchPreference(searchPreference: SearchPreference)

}