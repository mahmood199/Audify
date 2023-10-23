package com.example.scrutinizing_the_service.v2.ui.home.songs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.data.models.local.RecentlyPlayed
import com.example.scrutinizing_the_service.v2.ui.common.ContentLoaderUI
import com.example.scrutinizing_the_service.v2.util.convertToAbbreviatedViews
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun SongRowItemUI(
    recentlyPlayed: RecentlyPlayed,
    onItemClicked: (RecentlyPlayed) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClicked(recentlyPlayed)
            },
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            imageModel = {
                recentlyPlayed.imageUrl
            },
            failure = {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        R.drawable.ic_album_place_holder
                    ),
                    contentDescription = "Album place holder",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .clip(RoundedCornerShape(15))
                        .background(Color.Gray)
                        .padding(12.dp)
                )
            },
            loading = {
                ContentLoaderUI(
                    modifier = Modifier.align(Alignment.Center)
                )
            },
            modifier = Modifier
                .clip(shape = RoundedCornerShape(percent = 10))
                .fillMaxWidth(0.2f)
                .aspectRatio(1f),
        )

        Column {
            Text(
                text = recentlyPlayed.name,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )

            if (recentlyPlayed.label.isNotEmpty()) {
                Text(
                    text = recentlyPlayed.label,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
            }

            val plays = rememberSaveable() {
                recentlyPlayed.playCount.convertToAbbreviatedViews()
            }

            if (plays.isNotEmpty()) {
                Text(
                    text = "$plays plays",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
            }


        }
    }
}

@Preview
@Composable
fun SongRowItemUIPreview() {
    ScrutinizingTheServiceTheme {
        SongRowItemUI(
            recentlyPlayed = sample,
            onItemClicked = {}
        )
    }
}