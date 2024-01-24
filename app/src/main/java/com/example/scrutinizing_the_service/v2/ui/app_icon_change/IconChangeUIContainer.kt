package com.example.scrutinizing_the_service.v2.ui.app_icon_change

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        backPress = backPress
    )
}

@Composable
fun IconChangeUI(
    state: IconChangeViewState,
    backPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray),
    ) {

        item(key = "icon_change_page_title") {
            AppBar(
                imageVector = Icons.Default.ArrowBack,
                title = "Change App Icon",
                backPressAction = backPress,
                modifier = Modifier.padding(top = 32.dp)
            )
        }
    }
}


@Preview
@Composable
fun IconChangeUIPreview() {
    ScrutinizingTheServiceTheme {
        IconChangeUI(
            state = IconChangeViewState.default(),
            backPress = {

            }
        )
    }
}