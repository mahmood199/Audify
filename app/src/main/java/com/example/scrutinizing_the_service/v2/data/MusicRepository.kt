package com.example.scrutinizing_the_service.v2.data

import android.content.Context
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.platform.MusicLocatorV2
import com.example.scrutinizing_the_service.platform.MusicLocatorV4
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class MusicRepository @Inject constructor(
    @ApplicationContext val context: Context,
) {

    fun getMusicV2(): List<Song> {
        return MusicLocatorV2.fetchAllAudioFilesFromDevice(context)
    }

    fun getMusicV4(): List<Song> {
        return MusicLocatorV4.getAudioData(context)
    }

}