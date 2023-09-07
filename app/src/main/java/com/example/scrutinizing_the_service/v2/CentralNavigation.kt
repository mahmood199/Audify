package com.example.scrutinizing_the_service.v2

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scrutinizing_the_service.v2.list.MusicListUI

@Composable
fun NavigationCentral(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenName.AUDIO_LIST,
        modifier = modifier
    ) {
        composable(ScreenName.AUDIO_LIST) {
            MusicListUI()
        }
    }
}