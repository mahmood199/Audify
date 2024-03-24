package com.example.audify.v2.ui.app_icon_change

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.graphics.Color
import com.example.audify.MainCoroutineRule
import com.example.audify.getOrAwaitValue
import com.skydiver.audify.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class IconChangeViewModelTest {

    private val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var viewModel: IconChangeViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = IconChangeViewModel()
        Dispatchers.setMain(mainCoroutineRule.testDispatcher)
    }

    @Test
    fun getState() {
    }

    @Test
    fun getAppIcons() = runTest {
        val sut = IconChangeViewModel()
        sut.addIconsToState()

        val data = sut.stateLiveData.getOrAwaitValue()

        advanceUntilIdle()

        assertEquals(0, data.icons.toList().size)
    }

    @Test
    fun `test-App-Icons-View-State`() = runTest {
        // Mock the list of icon models
        val iconsList = listOf(
            IconModel(
                1,
                Color(R.color.ic_app_launcher_v1_background),
                IconVariant.Variant1
            ),
            IconModel(
                2,
                Color(R.color.ic_app_launcher_v2_background),
                IconVariant.Variant2
            ),
            IconModel(
                3,
                Color(R.color.ic_app_launcher_v3_background),
                IconVariant.Variant3
            ), // Add other IconModel instances as needed
        )

        // Mock the expected state after adding icons
        val expectedState = IconChangeViewState(isLoading = false, icons = iconsList.asSequence())

        // Stub the getIconModels() function
        `when`(viewModel.getIconModels()).thenReturn(iconsList.asSequence())

        // Call the function to be tested
        viewModel.addIconsToState()

        // Verify that the stateLiveData has been updated correctly
        assertEquals(expectedState, viewModel.stateLiveData.value)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}