package com.example.audify.testing

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull

class UserRepository {

    val users = mutableListOf<String>()

    private val _usersFlow = MutableStateFlow<String?>(null)
    val usersFlow = _usersFlow.asStateFlow().filterNotNull()

    fun register(name: String) {
        users.add(name)
    }

    fun emit(name: String) {
        _usersFlow.value = name
    }
}