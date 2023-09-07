package com.example.scrutinizing_the_service.v2

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scrutinizing_the_service.v2.list.MusicListUI

@Composable
fun NavigationCentral() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenName.AUDIO_LIST) {
        composable(ScreenName.AUDIO_LIST) {
            MusicListUI()
        }
    }
}