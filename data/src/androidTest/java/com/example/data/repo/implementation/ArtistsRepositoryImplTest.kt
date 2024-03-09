package com.example.data.repo.implementation

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.runner.AndroidJUnitRunner
import com.example.data.local.datasource.ArtistsLocalDataSource
import com.example.data.mapper.ArtistsMapper
import com.example.data.remote.core.NetworkResult
import com.example.data.remote.saavn.ArtistsRemoteDataSource
import com.example.data.repo.implementations.ArtistsRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(AndroidJUnit4::class)
class ArtistsRepositoryImplTest {

    @Mock
    lateinit var remoteDataSource: ArtistsRemoteDataSource

    @Mock
    lateinit var localDataSource: ArtistsLocalDataSource

    private lateinit var sut: ArtistsRepositoryImpl

    @Before
    fun setup(): Unit = runBlocking {
        MockitoAnnotations.openMocks(this)

        sut = ArtistsRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            mapper = ArtistsMapper()
        )

        Mockito.`when`(remoteDataSource.getArtistData(anyString())).thenReturn(
            NetworkResult.Exception(
                Throwable("Some error")
            )
        )
        Mockito.`when`(localDataSource.getAll()).thenReturn(emptyList())
        Mockito.`when`(localDataSource.getById(anyString())).thenReturn(null)
    }

    @Test
    fun getAll(): Unit = runBlocking {

        val result = sut.getById("")
        assertEquals(null, result)
    }

    @Test
    fun add() {
    }

    @Test
    fun getById() {
    }

    @Test
    fun getArtistInfo() {
    }

    @After
    fun tearDown() {
    }
}
