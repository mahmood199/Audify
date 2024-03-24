package com.example.data.remote.last_fm

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.models.remote.last_fm.AlbumListResponse
import com.example.data.models.remote.saavn.ArtistDetailResponse
import com.example.data.remote.core.LastFmClient
import com.example.data.remote.core.ResponseProcessor
import com.example.data.remote.core.SaavnClient
import com.example.data.remote.saavn.safeApiCall
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ArtistsRemoteDataSourceTest {

    @get:Rule()
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var responseProcessor: ResponseProcessor

    @Inject
    @LastFmClient
    lateinit var client: HttpClient


    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getArtistData() = runBlocking {
        val response = client.get {
            parameter("method", "tag.gettopalbums")
        }
        val result = responseProcessor.getResultFromResponse<AlbumListResponse>(gson, response)
    }
}