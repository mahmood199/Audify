package com.example.scrutinizing_the_service.v2.ui.practice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.ui.core.fade2
import com.example.scrutinizing_the_service.v2.ui.core.fadedBackground

@Composable
fun SampleText(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
    ) {
        Text(
            text = "Sample text",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .matchParentSize()
                .background(Color.Red)
                .background(Color.Green)
        )
        Image(
            painter = painterResource(R.drawable.ic_account_settings),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Red),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize().fadedBackground()
        )
    }
}

@Preview
@Composable
fun SampleTextPreview() {
    ScrutinizingTheServiceTheme {
        Column(verticalArrangement = Arrangement.Center) {
            SampleText()
        }
    }
}