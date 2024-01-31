package com.example.audify.v2.ui.home.songs

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.audify.v2.theme.ScrutinizingTheServiceTheme
import com.example.audify.v2.ui.common.ContentLoaderUI
import com.example.audify.v2.util.convertToAbbreviatedViews
import com.example.data.models.remote.saavn.Song
import com.skydiver.audify.R
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun SongRowItemUI(
    song: Song,
    onItemClicked: (Song) -> Unit,
    updateFavourite: (Song) -> Unit,
    modifier: Modifier = Modifier
) {

    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    var pressOffset by remember {
        mutableStateOf(Offset.Zero)
    }

    val density = LocalDensity.current

    BoxWithConstraints {
        Row(
            modifier = modifier
                .fillMaxWidth(0.95f)
                .pointerInput(true) {
                    detectTapGestures(
                        onTap = {
                            onItemClicked(song)
                        },
                        onLongPress = {
                            expanded = true
                            pressOffset = it
                        },
                        onDoubleTap = {
                            updateFavourite(song)
                        }
                    )
                },
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                imageModel = {
                    if (song.image.isNotEmpty()) {
                        song.image.last().link
                    } else {
                        ""
                    }
                },
                failure = {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            R.drawable.ic_album_place_holder
                        ),
                        contentDescription = "Song Thumbnail place holder",
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

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = song.name,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )

                if (song.label.isNotEmpty()) {
                    Text(
                        text = song.label,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                }

                val plays = rememberSaveable() {
                    song.playCount.convertToAbbreviatedViews()
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

                /*
                                val downloadUrl = remember {
                                    if (song.downloadUrl.isEmpty())
                                        ""
                                    else
                                        song.downloadUrl.last().link
                                }

                                Text(
                                    text = downloadUrl,
                                    style = MaterialTheme.typography.bodyMedium,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = song.url,
                                    style = MaterialTheme.typography.bodyMedium,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.Bold
                                )
                */
            }

            val iconToShow = remember(song) {
                R.drawable.ic_favorite
            }

            Icon(
                imageVector = ImageVector.vectorResource(iconToShow),
                tint = Color.Red,
                contentDescription = "Favourites icon"
            )
        }

        val (xDp, yDp) = with(density) {
            (pressOffset.x.toDp()) to (pressOffset.y.toDp())
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            offset = DpOffset(xDp, -yDp)
        ) {
            dropDownOptions.forEach {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = it,
                            modifier = Modifier
                                .padding(vertical = 2.dp)
                        )
                    },
                    onClick = {
                        expanded = false
                    },
                    contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
                )
            }
        }
    }
}

private val dropDownOptions = listOf(
    "Play Next",
    "Add to playlist",
    "Add to favourites",
    "Recommend to a friend",
    "Report this",
)

@Preview
@Composable
fun SongRowItemUIPreview() {
    ScrutinizingTheServiceTheme {
        SongRowItemUI(
            song = Song.default(),
            onItemClicked = {},
            updateFavourite = {}
        )
    }
}