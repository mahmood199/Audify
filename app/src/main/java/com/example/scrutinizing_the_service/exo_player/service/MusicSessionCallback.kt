package com.example.scrutinizing_the_service.exo_player.service

import android.media.session.MediaSession
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log

class MusicSessionCallback: MediaSessionCompat.Callback() {

    companion object {
        const val TAG = "MusicSessionCallback"
    }

    override fun onPlay() {
        super.onPlay()
        Log.d(TAG, "onPlay")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onSeekTo(pos: Long) {
        super.onSeekTo(pos)
        Log.d(TAG, "onSeekTo")
    }

    override fun onSkipToNext() {
        super.onSkipToNext()
        Log.d(TAG, "onSkipToNext")
    }

    override fun onSkipToPrevious() {
        super.onSkipToPrevious()
        Log.d(TAG, "onSkipToPrevious")
    }

    override fun onFastForward() {
        super.onFastForward()
        Log.d(TAG, "onFastForward")
    }

    override fun onRewind() {
        super.onRewind()
        Log.d(TAG, "onRewind")
    }

}