package com.example.scrutinizing_the_service.v2.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scrutinizing_the_service.TimeConverter
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.v2.common.AppBar

@Composable
fun MusicListUI(
    modifier: Modifier = Modifier,
    viewModel: MusicListViewModel = hiltViewModel()
) {
    val listState = rememberLazyListState()
    val isShown by remember {
        derivedStateOf {
            (listState.firstVisibleItemIndex + 1) < listState.layoutInfo.visibleItemsInfo.count()
        }
    }

    val loading by viewModel.loading.collectAsState()

    val slideInProgress = animateFloatAsState(
        targetValue = 1.0f,
        label = "Target"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        Scaffold(
            topBar = {
                AppBar(
                    imageVector = Icons.Default.ArrowBack,
                    title = "Catalog",
                    backPressAction = { },
                )
            },
            modifier = modifier
                .fillMaxSize()
        ) { paddingValues ->
            MusicListContent(
                isLoading = loading,
                songs = viewModel.songs,
                lazyListState = listState,
                modifier = Modifier
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding()
                    )
            )
        }


        AnimatedVisibility(
            visible = isShown,
            enter = slideIn(initialOffset = { IntOffset(100, 0) }),
            exit = slideOut(targetOffset = { IntOffset(200, 0) }),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .clip(RoundedCornerShape(25))
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Song Button"
                )
            }
        }
    }
}

@Composable
fun MusicListContent(
    isLoading: Boolean,
    songs: List<Song>,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = isLoading,
        label = "Loading Animation",
        modifier = modifier
    ) {
        if (it) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            MusicList(
                songs = songs,
                lazyListState = lazyListState,
            )
        }
    }
}

@Composable
fun MusicList(
    songs: List<Song>,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(all = 12.dp),
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray),
        state = lazyListState
    ) {
        itemsIndexed(items = songs, key = { index: Int, item: Song ->
            item.name + index
        }) { _, item ->
            SongUI(item)
        }
    }
}

@Composable
fun SongUI(item: Song) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .padding(all = 6.dp)
    ) {
        Text(
            text = item.name,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = item.artist,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleSmall
        )
        val duration = remember {
            TimeConverter.getConvertedTime(item.duration.toLong())
        }
        Text(
            text = duration,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) {
        mutableIntStateOf(firstVisibleItemIndex)
    }
    var previousScrollOffset by remember(this) {
        mutableIntStateOf(firstVisibleItemScrollOffset)
    }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}
