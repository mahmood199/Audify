package com.example.scrutinizing_the_service

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
/*
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class QuickPickViewModelTest {

    @org.junit.Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var landingPageRepository: LandingPageRepositoryImpl

    @Inject
    lateinit var artistsRepository: ArtistsRepository

    @Inject
    lateinit var genreRepository: GenreRepository

    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
        hiltRule.inject()
    }

    @Test
    fun testLandingPageRepository() {
        runTest {
            launch(dispatcherProvider.io) {
                when (val result = landingPageRepository.getLandingPageData()) {
                    is NetworkResult.Success -> {
                        println("Albums")
                        result.data.data?.albums?.forEach {
                            println(it.id)
                        }

                        println("Charts")
                        result.data.data?.charts?.forEach {
                            println(it.id)
                        }

                        println("Playlists")
                        result.data.data?.playlists?.forEach {
                            println(it.id)
                        }

                    }

                    else -> {

                    }
                }
            }
        }
    }

    @After
    fun doSomethingElse() {

    }

}*/
