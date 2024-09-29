package com.example.viewmodel

import android.util.Log
import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.example.audify.v2.testing_components.App
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppTest {

    companion object {
        const val TAG = "AppTest"
    }

    @Test
    fun test1() = runTest {
        val app = App()
        println(TAG + Thread.currentThread().name)
        app.stateFlow.test {
            app.start()
            println(TAG + Thread.currentThread().name)

            assert(awaitItem() == 0)
            assert(awaitItem() == 1)
            assert(awaitItem() == 2)
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun test2() = runTest {
        turbineScope {

            val app = App()
            val items = app.stateFlow.testIn(this)

            app.start()
            assert(items.awaitItem() == 0)
            assert(items.awaitItem() == 1)
            advanceTimeBy(3000)
            assert(items.awaitItem() == 10)
            items.cancelAndIgnoreRemainingEvents()
        }
    }

}