package com.example.audify

import okhttp3.mockwebserver.MockResponse
import java.net.HttpURLConnection

fun mockResponse(responseBody: String, status: Int = HttpURLConnection.HTTP_OK) =
    MockResponse()
        .setResponseCode(status)
        .setBody(responseBody)
