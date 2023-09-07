package com.example.scrutinizing_the_service.v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AudioPlayerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScrutinizingTheServiceTheme {
                NavigationCentral()
            }
        }
    }
}

@Preview
@Composable
fun NavigationCentralPreview() {
    ScrutinizingTheServiceTheme {
        NavigationCentral(
            modifier = Modifier.background(
                MaterialTheme.colorScheme.surface
            )
        )
    }
}