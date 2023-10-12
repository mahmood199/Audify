package com.example.scrutinizing_the_service.v2.ui.landing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Album
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.ArtistData
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Playlist
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Song
import com.example.scrutinizing_the_service.v2.ui.common.ContentLoaderUI
import com.example.scrutinizing_the_service.v2.ui.common.LoadMoreItemsRowUI
import com.example.scrutinizing_the_service.v2.ui.common.SnappingLazyRow
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun QuickPicksUI(
    modifier: Modifier = Modifier,
    viewModel: QuickPickViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val albums = viewModel.albums.toPersistentList()
    val songs = viewModel.songs.toPersistentList()
    val artists = viewModel.artists.toPersistentList()
    val playlists = viewModel.playlists.toPersistentList()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Crossfade(
            targetState = state.isLoading,
            label = "CrossFade"
        ) { isLoading ->
            if (isLoading) {
                ContentLoaderUI()
            } else {
                QuickPicksUIContent(
                    albums = albums,
                    songs = songs,
                    artists = artists,
                    playlists = playlists
                )
            }
        }
    }
}

@Composable
fun QuickPicksUIContent(
    albums: PersistentList<Album>,
    songs: PersistentList<Song>,
    artists: PersistentList<ArtistData>,
    playlists: PersistentList<Playlist>,
    modifier: Modifier = Modifier
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        AlbumCatalogs(
            albums = albums
        )

        ArtistCatalogs(
            artists = artists,
        )

        SongCatalogs(
            songs = songs
        )

        PlaylistCatalogs(
            playlists = playlists,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}

@Composable
fun PlaylistCatalogs(
    playlists: PersistentList<Playlist>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            text = "Trending Playlists",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold
        )

        SnappingLazyRow(
            items = playlists,
            itemWidth = LocalConfiguration.current.screenWidthDp.dp,
            onSelect = {},
            key = { index, item ->
                item.id + item.firstname + index
            },
            modifier = Modifier
                .fillMaxWidth(),
            item = { index, playlist, scale ->
                PlayListItemUI(
                    album = playlist,
                    imageWidthRatio = 0.35f,
                    modifier = Modifier
                        .width(LocalConfiguration.current.screenWidthDp.dp * 0.8f)
                        .scale(scale = scale)
                        .padding(12.dp)
                )
            }
        )

        Spacer(modifier = Modifier.height(120.dp))
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumCatalogs(
    albums: PersistentList<Album>,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        Text(
            text = "Albums",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold
        )

        val lazyGridState = rememberLazyGridState()

        val snappingLayout = remember(lazyGridState) {
            SnapLayoutInfoProvider(
                lazyGridState = lazyGridState
            ) { _, _, _ -> lazyGridState.firstVisibleItemIndex }
        }

        val flingBehavior = rememberSnapFlingBehavior(snappingLayout)

        BoxWithConstraints(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        ) {

            LazyHorizontalGrid(
                modifier = Modifier
                    .height(400.dp),
                state = lazyGridState,
                rows = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                flingBehavior = flingBehavior,
            ) {
                items(
                    count = albums.size,
                    key = {
                        albums[it].id
                    },
                    contentType = {
                        "album"
                    }
                ) {
                    AlbumItemUI(
                        album = albums[it],
                        modifier = Modifier.width(maxWidth * 0.9f)
                    )
                }
                item(
                    key = "See More",
                    span = {
                        GridItemSpan(4)
                    }
                ) {
                    LoadMoreItemsRowUI(
                        modifier = Modifier
                            .width(maxWidth * 0.25f)
                            .padding(12.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtistCatalogs(
    artists: PersistentList<ArtistData>,
    modifier: Modifier = Modifier
) {

    AnimatedVisibility(artists.size > 3) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Artists",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )

            val lazyListState = rememberLazyListState()

            LazyRow(
                state = lazyListState,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                contentPadding = PaddingValues(horizontal = 12.dp),
            ) {
                items(
                    count = artists.size,
                    key = { index ->
                        artists[index].id + index
                    }
                ) {
                    ArtistItemUI(
                        artist = artists[it],
                        modifier = Modifier
                            .fillParentMaxWidth(0.3f)
                            .animateItemPlacement()
                            .animateContentSize()
                    )
                }
                item(
                    key = "See More 2",
                ) {
                    LoadMoreItemsRowUI(
                        modifier = Modifier
                            .fillParentMaxWidth(0.3f)
                            .aspectRatio(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun SongCatalogs(
    songs: PersistentList<Song>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Songs",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            items(
                count = songs.size,
                key = { index ->
                    songs[index].id + index
                }
            ) {
                SongItemUI(
                    song = songs[it],
                    modifier = Modifier
                        .fillParentMaxWidth(0.25f)
                )
            }

            item(
                key = "See More 2",
            ) {
                LoadMoreItemsRowUI(
                    modifier = Modifier
                        .fillParentMaxWidth(0.3f)
                        .aspectRatio(1f)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewQuickPicksUI() {
    ScrutinizingTheServiceTheme {
        QuickPicksUI()
    }
}