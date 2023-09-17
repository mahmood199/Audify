package com.example.scrutinizing_the_service.v2.data.repo.implementations

import android.content.Context
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.platform.MusicLocatorV2
import com.example.scrutinizing_the_service.platform.MusicLocatorV4
import com.example.scrutinizing_the_service.v2.data.remote.DemoRemoteDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject


class MusicRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val demoRemoteDataSource: DemoRemoteDataSource
) {

    fun getMusicV2(): List<Song> {
        return MusicLocatorV2.fetchAllAudioFilesFromDevice(context)
    }

    fun getMusicV4(): List<Song> {
        return MusicLocatorV4.getAudioData(context)
    }

    suspend fun fetchSongFromRemote(): HttpResponse {
        return demoRemoteDataSource.getTopAlbums()
    }

}