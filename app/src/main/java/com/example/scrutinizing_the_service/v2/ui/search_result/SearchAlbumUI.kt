package com.example.scrutinizing_the_service.v2.ui.search_result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme

@Composable
fun SearchAlbumUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {

    }
}


@Preview
@Composable
fun SearchAlbumUIPreview() {
    ScrutinizingTheServiceTheme {
        SearchAlbumUI()
    }
}