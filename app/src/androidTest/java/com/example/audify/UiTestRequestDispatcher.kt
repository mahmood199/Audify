package com.example.audify

import android.content.Context
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import java.net.HttpURLConnection
import okhttp3.mockwebserver.RecordedRequest

typealias MockRequestHandler = (request: RecordedRequest) -> MockResponse

class UiTestRequestDispatcher(private val context: Context) : Dispatcher() {

    private val simpleResponses = mutableMapOf<String, MockResponse>()
    private val complexResponses = mutableMapOf<String, MockRequestHandler>()

    fun addResponse(
        pathPattern: String,
        filename: String,
        httpMethod: String = "GET",
        status: Int = HttpURLConnection.HTTP_OK
    ) {
        val response = mockResponse(UiTestFileUtils.readFile(context, filename), status)
        val responseKey = "$httpMethod/$pathPattern"
        // adding the http method into the key allows for a repeated pathPattern
        // that is used by both GET and POST to behave differently for eg.
        if (simpleResponses[responseKey] != null) {
            simpleResponses.replace(responseKey, response)
        } else {
            simpleResponses[responseKey] = response
        }
    }

    fun addResponse(
        pathPattern: String,
        requestHandler: MockRequestHandler,
        httpMethod: String = "GET",
    ) {
        val responseKey = "$httpMethod/$pathPattern"
        if (complexResponses[responseKey] != null) {
            complexResponses.replace(responseKey, requestHandler)
        } else {
            complexResponses[responseKey] = requestHandler
        }
    }

    override fun dispatch(request: RecordedRequest): MockResponse {
        println("Incoming request: $request")
        Thread.sleep(200) // provide a small delay to better mimic real life network call across a mobile network
        val responseKey = request.method + request.path

        var response = findComplexResponse(responseKey, request)

        if (response == null) {
            response = findSimpleResponse(responseKey)
        }

        if (response == null) {
            println("no response found for $responseKey")
            response = errorResponse(responseKey)
        }
        return response
    }

    private fun findComplexResponse(responseKey: String, request: RecordedRequest): MockResponse? {
        for (pathPattern in complexResponses.keys) {
            if (responseKey.matches(Regex(pathPattern))) {
                val handler = complexResponses[pathPattern]
                if (handler != null) {
                    return handler(request)
                }
            }
        }
        return null
    }

    private fun findSimpleResponse(responseKey: String): MockResponse? {
        for (pathPattern in simpleResponses.keys) {
            if (responseKey.matches(Regex(pathPattern))) {
                val response = simpleResponses[pathPattern]
                if (response != null) {
                    return response
                }
            }
        }
        return null
    }

    private fun errorResponse(reason: String): MockResponse {
        return mockResponse("""{"error":"response not found for "$reason"}""", HttpURLConnection.HTTP_INTERNAL_ERROR)
    }
}