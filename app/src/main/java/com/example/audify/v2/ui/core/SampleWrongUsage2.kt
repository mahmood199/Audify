package com.example.audify.v2.ui.core

import androidx.compose.foundation.background
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


val extractedModifier = Modifier.background(Color.Red) // Hoisted to save allocations

@Composable
fun Modifier.composableModifier(): Modifier {
    val color = LocalContentColor.current.copy(alpha = 0.5f)
    return this then Modifier.background(color)
}

@Composable
fun MyComposable() {
    val composedModifier = Modifier.composableModifier() // Cannot be extracted any higher
}