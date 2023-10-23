package com.example.scrutinizing_the_service.v2.ui.home.songs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.scrutinizing_the_service.v2.data.models.local.Genre

@Composable
fun GenreUiItem(
    genre: Genre,
    isSelected: Boolean,
    onGenreClicked: (Genre) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = genre.name,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(if (isSelected) Color.DarkGray else Color.Blue)
            .clickable {
                onGenreClicked(genre)
            }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}