package com.example.audify

import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import okhttp3.mockwebserver.MockWebServer
import org.junit.AfterClass
import org.junit.BeforeClass
import java.net.HttpURLConnection

open class BaseUiTest {

    lateinit var navController: TestNavHostController

    init {
        initialiseNavController()
    }

    fun initialiseNavController() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())
    }

    companion object {
        const val MOCK_SERVER_PORT = 47777
        val BASE_URL = "http://localhost:$MOCK_SERVER_PORT"
        protected val dispatcher = UiTestRequestDispatcher(UiTestUtils.testContext)

        protected var webServer: MockWebServer? = null

        @BeforeClass
        @JvmStatic
        fun startMockServer() {
            if (webServer == null) {
                println("Mock Web Server starting")
                webServer = MockWebServer()
                webServer!!.start(MOCK_SERVER_PORT)
                webServer!!.dispatcher = dispatcher
            }
        }
        @AfterClass
        @JvmStatic
        fun shutDownServer() {
            webServer?.shutdown()
            webServer = null
        }
    }

    fun addResponse(
        pathPattern: String,
        filename: String,
        httpMethod: String = "GET",
        status: Int = HttpURLConnection.HTTP_OK
    ) = dispatcher.addResponse(pathPattern, filename, httpMethod, status)

    fun addResponse(
        pathPattern: String,
        requestHandler: MockRequestHandler,
        httpMethod: String = "GET",
    ) = dispatcher.addResponse(pathPattern, requestHandler, httpMethod)

}