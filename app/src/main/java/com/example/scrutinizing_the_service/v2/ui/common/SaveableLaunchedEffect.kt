package com.example.scrutinizing_the_service.v2.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.CoroutineScope

@Composable
fun SaveableLaunchedEffect(
    key: Any,
    trigger: Boolean = false,
    block: suspend CoroutineScope.() -> Unit,
) {
    val isEffectAlreadyTriggered = rememberSaveable { mutableStateOf(trigger) }
    LaunchedEffect(key1 = key) {
        if (isEffectAlreadyTriggered.value) return@LaunchedEffect
        block()
        isEffectAlreadyTriggered.value = true
    }
}
