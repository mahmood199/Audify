package com.example.scrutinizing_the_service.v2.ui.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme

@Composable
fun ClockUI(
    modifier: Modifier = Modifier
) {

    Canvas(modifier = Modifier
        .fillMaxSize()
        ) {

    }
}

@Preview
@Composable
fun ClockUUIPreview() {
    ScrutinizingTheServiceTheme {
        ClockUI()
    }
}