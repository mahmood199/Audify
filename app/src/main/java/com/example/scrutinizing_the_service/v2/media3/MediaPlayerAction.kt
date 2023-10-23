package com.example.scrutinizing_the_service.v2.media3


sealed class MediaPlayerAction {
    data object PlayPause : MediaPlayerAction()
    data object Rewind : MediaPlayerAction()
    data object FastForward : MediaPlayerAction()
    data object PlayPreviousItem : MediaPlayerAction()
    data object PlayNextItem : MediaPlayerAction()
    data class PlaySongAt(val index: Int) : MediaPlayerAction()
    data class UpdateProgress(val newProgress: Float) : MediaPlayerAction()
}