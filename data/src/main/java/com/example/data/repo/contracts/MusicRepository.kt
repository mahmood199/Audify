package com.example.data.repo.contracts

import com.example.data.models.remote.saavn.Song

interface MusicRepository {

    fun getMusicV1(): List<Song>

    fun getMusicV2(): List<Song>

    fun getMusicV3(): List<Song>

    fun getMusicV4(): List<Song>

}