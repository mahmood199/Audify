package com.example.scrutinizing_the_service.v2.data.repo.contracts

import com.example.scrutinizing_the_service.data.Song

interface MusicRepository {

    fun getMusicV1(): List<Song>

    fun getMusicV2(): List<Song>

    fun getMusicV3(): List<Song>

    fun getMusicV4(): List<Song>

}