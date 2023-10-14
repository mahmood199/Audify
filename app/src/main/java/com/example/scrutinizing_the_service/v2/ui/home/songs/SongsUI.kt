package com.example.scrutinizing_the_service.v2.ui.home.songs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme

@Composable
fun SongsUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {

    }
}


@Preview
@Composable
fun PreviewSongsUI() {
    ScrutinizingTheServiceTheme {
        SongsUI()
    }
}