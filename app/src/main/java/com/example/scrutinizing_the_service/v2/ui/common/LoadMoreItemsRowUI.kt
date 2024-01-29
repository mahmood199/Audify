package com.example.scrutinizing_the_service.v2.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.scrutinizing_the_service.v2.theme.ScrutinizingTheServiceTheme

@Composable
fun LoadMoreItemsRowUI(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
            Text(
                text = "Load More",
            )
        }
    }
}

@Preview
@Composable
fun PreviewLoadMoreItemsRowUI() {
    ScrutinizingTheServiceTheme {
        LoadMoreItemsRowUI(modifier = Modifier.fillMaxSize())
    }

}