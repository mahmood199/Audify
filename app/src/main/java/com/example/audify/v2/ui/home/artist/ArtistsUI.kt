package com.example.audify.v2.ui.home.artist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.audify.v2.theme.AudifyTheme

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
    AudifyTheme {
        ArtistsUI()
    }
}