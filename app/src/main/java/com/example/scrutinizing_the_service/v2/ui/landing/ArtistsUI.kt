package com.example.scrutinizing_the_service.v2.ui.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme

@Composable
fun ArtistsUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green)
    ) {

    }
}


@Preview
@Composable
fun PreviewArtistsUI() {
    ScrutinizingTheServiceTheme {
        ArtistsUI()
    }
}