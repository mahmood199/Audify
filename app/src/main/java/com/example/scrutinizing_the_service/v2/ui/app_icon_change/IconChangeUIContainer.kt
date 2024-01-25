package com.example.scrutinizing_the_service.v2.ui.app_icon_change

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.compose_utils.SaveableLaunchedEffect
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.ui.common.AppBar

@Composable
fun IconChangeUIContainer(
    backPress: () -> Unit,
    viewModel: IconChangeViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    IconChangeUI(
        state = state,
        backPress = backPress,
        initViewState = {
            viewModel.addIconsToState()
        }
    )
}

@Composable
fun IconChangeUI(
    state: IconChangeViewState,
    backPress: () -> Unit,
    initViewState: (Context) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    SaveableLaunchedEffect(Unit) {
        initViewState(context)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray),
    ) {

        item(
            key = "icon_change_page_title",
            span = {
                GridItemSpan(3)
            }
        ) {
            AppBar(
                imageVector = Icons.Default.ArrowBack,
                title = "Change App Icon",
                backPressAction = backPress,
                modifier = Modifier.padding(top = 32.dp)
            )
        }

        items(
            count = state.icons.size,
            span = {
                GridItemSpan(1)
            },
            key = {
                "${state.icons[it].resourceId + it}"
            }
        ) {
            val item = state.icons[it]
            AppIcon(item, modifier = Modifier)
        }
    }
}

@Composable
fun AppIcon(
    drawableResource: IconModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = painterResource(id = drawableResource.resourceId),
            contentDescription = "$drawableResource",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            tint = Color.Unspecified,
        )

        Text(text = "${drawableResource.resourceId}")
    }
}


@Preview
@Composable
fun IconChangeUIPreview() {
    ScrutinizingTheServiceTheme {
        val context = LocalContext.current
        val icons = listOf(
            R.mipmap.ic_app_launcher_v1,
            R.mipmap.ic_app_launcher_v2,
            R.mipmap.ic_app_launcher_v3,
            R.mipmap.ic_app_launcher_v4,
            R.mipmap.ic_app_launcher_v5,
            R.mipmap.ic_app_launcher_v6,
        )
        IconChangeUI(
            state = IconChangeViewState.default().copy(icons = emptyList()),
            backPress = {

            }, {

            }
        )
    }
}