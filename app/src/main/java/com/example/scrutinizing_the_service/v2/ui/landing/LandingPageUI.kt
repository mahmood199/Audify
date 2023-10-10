package com.example.scrutinizing_the_service.v2.ui.landing

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.ui.common.SideNavigationBar
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LandingPageUI(
    viewModel: LandingPageViewModel = hiltViewModel()
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

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .padding(
                        vertical = 20.dp,
                        horizontal = 12.dp
                    )
                    .fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                Text(
                    text = headers[selectedIndex].first,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.weight(1f)
                )
            }
        }
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
                modifier = Modifier.padding(start = 6.dp)
            ) {
                SideNavigationBar(
                    headers = headers,
                    selectedIndex = selectedIndex
                ) {
                    selectedIndex = it
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    VerticalPager(
                        state = pagerState,
                        userScrollEnabled = false
                    ) { currentPage ->
                        when (currentPage) {

                            QUICK_PICKS_PAGE_INDEX -> QuickPicksUI()

                            SONGS_PAGE_INDEX -> SongsUI()

                            PLAYLIST_PAGE_INDEX -> PlaylistUI()

                            ARTIST_PAGE_INDEX -> ArtistsUI()

                            ALBUM_PAGE_INDEX -> AlbumsUI()

                            else -> Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black)
                            )
                        }
                    }
                }
            }
        }
    }
}

private const val QUICK_PICKS_PAGE_INDEX = 0
private const val SONGS_PAGE_INDEX = 1
private const val PLAYLIST_PAGE_INDEX = 2
private const val ARTIST_PAGE_INDEX = 3
private const val ALBUM_PAGE_INDEX = 4


@Composable
private fun getHeaders(): PersistentList<Pair<String, ImageVector>> {
    return listOf(
        Pair("Quick Picks", ImageVector.vectorResource(R.drawable.ic_music_note)),
        Pair("Songs", ImageVector.vectorResource(R.drawable.ic_library_music)),
        Pair("Playlists", ImageVector.vectorResource(R.drawable.ic_artist)),
        Pair("Artists", ImageVector.vectorResource(R.drawable.ic_artist)),
        Pair("Albums", ImageVector.vectorResource(R.drawable.ic_artist)),
    ).toPersistentList()
}


@Preview
@Composable
fun PreviewLandingPageUI() {
    ScrutinizingTheServiceTheme {
        LandingPageUI()
    }
}