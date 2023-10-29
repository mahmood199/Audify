package com.example.scrutinizing_the_service.v2.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme

@Composable
fun FailedToLoadImage(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = ImageVector.vectorResource(
            R.drawable.ic_album_place_holder
        ),
        contentDescription = "Place holder",
        tint = MaterialTheme.colorScheme.onSecondary,
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(15))
            .background(Color.Gray)
            .padding(12.dp)
    )
}

@Preview
@Composable
fun FailedToLoadImagePreview() {
    ScrutinizingTheServiceTheme {
        FailedToLoadImage()
    }
}