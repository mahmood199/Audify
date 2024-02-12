package com.example.audify.v2.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.audify.v2.theme.AudifyTheme

@Composable
fun ContentLoaderUI(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Preview
@Composable
fun PreviewContentLoaderUI() {
    AudifyTheme {
        ContentLoaderUI()
    }
}