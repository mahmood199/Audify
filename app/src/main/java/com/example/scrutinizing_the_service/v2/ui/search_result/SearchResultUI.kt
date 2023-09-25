package com.example.scrutinizing_the_service.v2.ui.search_result

import androidx.activity.compose.BackHandler
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
fun SearchResultUI(
    query: String,
    backPress: () -> Unit,
    viewModel: SearchResultViewModel = hiltViewModel()
) {

    val headers = getHeaders()

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState(0, 0f) {
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
                    .padding(top = 12.dp)
                    .padding(horizontal = 12.dp)
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
                            0 -> SearchTrackUI()

                            1 -> SearchAlbumUI()

                            2 -> SearchArtistUI()

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

@Composable
fun getHeaders(): PersistentList<Pair<String, ImageVector>> {
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
            backPress = {

            }
        )
    }
}