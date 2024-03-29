package com.example.audify.v2.ui.home.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.audify.v2.ui.common.ContentLoaderUI
import com.example.data.models.remote.saavn.Playlist
import com.skydiver.audify.R
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PlayListItemUI(
    album: Playlist,
    modifier: Modifier = Modifier,
    imageWidthRatio: Float = 0.25f
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            GlideImage(
                imageModel = {
                    if (album.image.isEmpty()) {
                        ""
                    } else {
                        album.image.last().link
                    }
                },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.FillBounds
                ),
                modifier = Modifier
                    .fillMaxWidth(imageWidthRatio)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(5)),
                failure = {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            R.drawable.ic_album_place_holder
                        ),
                        contentDescription = "Playlist place holder",
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier
                            .clip(RoundedCornerShape(15))
                            .background(Color.Gray)
                            .padding(12.dp)
                    )
                },
                loading = {
                    ContentLoaderUI(modifier = Modifier.fillMaxSize())
                }
            )
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = album.title,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = album.subtitle,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Songs: ${album.songCount}",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
