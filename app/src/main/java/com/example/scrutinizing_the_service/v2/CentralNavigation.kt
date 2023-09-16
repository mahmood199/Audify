package com.example.scrutinizing_the_service.v2

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.v2.ui.MusicListUI

@Composable
fun NavigationCentral(
    playMusic: (Song, Int) -> Unit,
    backPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenName.AUDIO_LIST,
        modifier = modifier.background(Color.White)
    ) {
        composable(ScreenName.AUDIO_LIST) {
            MusicListUI(
                playMusic = playMusic,
                backPress = backPress
            )
        }
    }
}