package com.example.audify.v2.ui.home.album

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.audify.v2.theme.ScrutinizingTheServiceTheme

@Composable
fun AlbumsUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta)
    ) {

    }
}


@Preview
@Composable
fun PreviewAlbumsUI() {
    ScrutinizingTheServiceTheme {
        AlbumsUI()
    }
}