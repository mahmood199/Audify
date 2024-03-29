package com.example.audify.v2.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.audify.v2.theme.AudifyTheme

@Composable
fun AppBar(
    imageVector: ImageVector,
    title: String,
    backPressAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "Back Navigation Button for $title",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .size(28.dp)
                .clickable {
                    backPressAction()
                }
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun AppBarPreview() {
    AudifyTheme {
        AppBar(
            imageVector = Icons.Default.ArrowBack,
            title = "Demo",
            backPressAction = { },
            modifier = Modifier.background(Color.White)
        )
    }
}