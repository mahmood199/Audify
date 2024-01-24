package com.example.scrutinizing_the_service.v2


object ScreenName {
    const val AUDIO_LIST = "audio-list"
    const val AUDIO_PLAYER = "audio-player"
    const val MAIN = "main"
    const val SEARCH_HISTORY = "search_history"
    const val SEARCH_RESULT = "search_result"
    const val LANDING_PAGE = "landing_page"
    const val SETTINGS_PAGE = "settings"
    const val GENRE_SELECTION = "genre_selection"
    const val AUDIO_DOWNLOAD_LIST = "audio_download_list"
    const val SHORT_CUT_SETUP = "short_cut_setup"
    const val CHANGE_ICON = "change_icon"
}


sealed class Screen(val name: String) {
    data object AudioList : Screen(name = ScreenName.AUDIO_LIST)
    data object AudioPlayer : Screen(name = ScreenName.AUDIO_PLAYER)
    data object Main : Screen(name = ScreenName.MAIN)
    data object SearchHistory : Screen(name = ScreenName.SEARCH_HISTORY)
    data object SearchResult : Screen(name = ScreenName.SEARCH_RESULT)
    data object LandingPage : Screen(name = ScreenName.LANDING_PAGE)
    data object SettingsPage : Screen(name = ScreenName.SETTINGS_PAGE)
    data object GenreSelection : Screen(name = ScreenName.GENRE_SELECTION)
    data object AudioDownloadListScreen : Screen(name = ScreenName.AUDIO_DOWNLOAD_LIST)
    data object ShortcutScreen : Screen(name = ScreenName.SHORT_CUT_SETUP)
    data object ChangeIcon : Screen(name = ScreenName.CHANGE_ICON)
}