package com.example.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.local.core.ApplicationDatabase
import com.example.data.local.util.getOrAwaitValue
import com.example.data.models.local.Genre
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GenreDaoTesting {

    lateinit var database: ApplicationDatabase

    lateinit var dao: GenreDao

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room
            .inMemoryDatabaseBuilder(
                context = ApplicationProvider.getApplicationContext(),
                klass = ApplicationDatabase::class.java
            )
            .allowMainThreadQueries()
            .build()

        dao = database.genreDao()
    }

    @Test
    fun insertItem(): Unit = runBlocking {
        (0..4).forEachIndexed { index, i ->
            val genre = Genre(name = "$index Genre", userSelected = true)
            dao.add(genre)
        }

        val genreList = dao.getAllAsLiveData().getOrAwaitValue()

        val lastGenre = dao.getById(4)

        assertEquals(true, lastGenre != null)
        assertEquals(5, genreList.size)
        assertEquals(true, genreList.map { it.userSelected }.all { it })
    }

    @Test
    fun deleteItem(): Unit = runBlocking {
        val genre = Genre(name = "1 Genre", userSelected = true)
        dao.add(genre)
        val genreList = dao.getAllAsLiveData().getOrAwaitValue()
        assertEquals(1, genreList.size)
        dao.delete(genre)
    }

    @After
    fun tearDown() {
        database.close()
    }

}