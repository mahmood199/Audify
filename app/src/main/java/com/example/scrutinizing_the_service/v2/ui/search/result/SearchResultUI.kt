package com.example.scrutinizing_the_service.v2.ui.search.result

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.ui.common.BottomPlayer
import com.example.scrutinizing_the_service.v2.ui.common.SideNavigationBar
import com.example.scrutinizing_the_service.v2.ui.search.album.SearchAlbumUI
import com.example.scrutinizing_the_service.v2.ui.search.artist.SearchArtistUI
import com.example.scrutinizing_the_service.v2.ui.search.track.SearchTrackUI
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchResultUI(
    query: String,
    backPress: () -> Unit,
    navigateToPlayer: () -> Unit,
    viewModel: SearchResultViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val headers = getHeaders()

    var selectedIndex by remember {
        mutableIntStateOf(value = state.userSelectedPage)
    }

    val pagerState = rememberPagerState(
        initialPage = state.userSelectedPage,
        initialPageOffsetFraction = 0f
    ) {
        headers.size
    }

    BackHandler {
        backPress()
    }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Text(
                text = query,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .padding(horizontal = 12.dp)
            )
        },
        bottomBar = {
            AnimatedBottomPlayer(
                state = state,
                selectedPage = selectedIndex,
                sendUiEvent = viewModel::sendUIEvent,
                navigateToPlayer = navigateToPlayer
            )
        },
        modifier = Modifier.background(Color.Gray)
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(12.dp)
            ) {
                SideNavigationBar(
                    headers = headers,
                    selectedIndex = selectedIndex,
                    onItemSelected = {
                        selectedIndex = it
                        viewModel.setPreference(selectedIndex)
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(it)
                        }
                    }
                )

                Column(modifier = Modifier.weight(1f)) {
                    VerticalPager(
                        state = pagerState,
                        userScrollEnabled = false
                    ) { currentPage ->
                        when (currentPage) {
                            TRACK_PAGE_INDEX -> SearchTrackUI()

                            ALBUM_PAGE_INDEX -> SearchAlbumUI()

                            ARTIST_PAGE_INDEX -> SearchArtistUI()

                            else -> Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Red)
                            )
                        }
                    }
                }
            }
        }
    }
}

private const val TRACK_PAGE_INDEX = 0
private const val ALBUM_PAGE_INDEX = 1
private const val ARTIST_PAGE_INDEX = 2

@Composable
private fun AnimatedBottomPlayer(
    state: SearchResultViewState,
    selectedPage: Int,
    sendUiEvent: (SearchResultUiEvent) -> Unit,
    navigateToPlayer: () -> Unit
) {
    AnimatedVisibility(
        visible = state.isPlaying && selectedPage == TRACK_PAGE_INDEX,
        enter = slideIn(initialOffset = { IntOffset(0, 100) }),
        exit = slideOut(targetOffset = { IntOffset(0, 200) }),
    ) {
        BottomPlayer(
            progress = state.progress,
            songName = state.currentSong?.name ?: "Error",
            artist = state.currentSong?.artist ?: "Error",
            isPlaying = state.isPlaying,
            onPlayPauseClicked = {
                sendUiEvent(SearchResultUiEvent.PlayPause)
            },
            onRewindClicked = {
                sendUiEvent(SearchResultUiEvent.Rewind)
            },
            onFastForwardClicked = {
                sendUiEvent(SearchResultUiEvent.FastForward)
            },
            onPlayPreviousClicked = {
                sendUiEvent(SearchResultUiEvent.PlayPreviousItem)
            },
            onPlayNextClicked = {
                sendUiEvent(SearchResultUiEvent.PlayNextItem)
            },
            navigateToPlayer = {
                navigateToPlayer()
            },
            seekToPosition = {
                sendUiEvent(SearchResultUiEvent.UpdateProgress(it))
            }
        )
    }
}


@Composable
private fun getHeaders(): PersistentList<Pair<String, ImageVector>> {
    return listOf(
        Pair("Tracks", ImageVector.vectorResource(R.drawable.ic_music_note)),
        Pair("Album", ImageVector.vectorResource(R.drawable.ic_library_music)),
        Pair("Artist", ImageVector.vectorResource(R.drawable.ic_artist)),
    ).toPersistentList()
}


@Preview
@Composable
fun SearchResultUIPreview() {
    ScrutinizingTheServiceTheme {
        SearchResultUI(
            query = "",
            navigateToPlayer = {},
            backPress = {

            }
        )
    }
}