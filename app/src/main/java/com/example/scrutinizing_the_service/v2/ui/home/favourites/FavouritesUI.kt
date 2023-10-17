package com.example.scrutinizing_the_service.v2.ui.home.favourites

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme

@Composable
fun FavouritesUI(
    modifier: Modifier = Modifier,
    viewModel: FavouritesViewModel = hiltViewModel()
) {

    var selectedTab by remember {
        mutableIntStateOf(0)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTab,
            indicator = {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .border(BorderStroke(2.dp, Color.White))
                )
            }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = {
                    selectedTab = 0
                },
            ) {
                Text("Online")
            }
            Tab(
                selected = selectedTab == 1,
                onClick = {
                    selectedTab = 1
                },
            ) {
                Text("Local")
            }
        }
    }
}


@Preview
@Composable
fun PreviewFavouritesUI() {
    ScrutinizingTheServiceTheme {
        FavouritesUI()
    }
}