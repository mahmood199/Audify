package com.example.audify.v2.testing_components

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class App {

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow: Flow<Int> = _stateFlow

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            _stateFlow.emit(1)
            delay(3000)
            _stateFlow.emit(2)
        }
    }


}