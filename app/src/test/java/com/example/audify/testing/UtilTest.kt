package com.example.audify.testing

import com.example.audify.MainCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UtilTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `getUserName with runBlocking`() = runBlocking {
        //Run blocking is not the right way to execute test
        val sut = Util(mainCoroutineRule.testDispatcher)
        val result = sut.getUserName()
        assertEquals(result, "CoroutineTesting")
    }

    @Test
    fun `getUserName with runTest`() = runTest {
        //Run Test is the right way to execute test. Because it optimises the delays in code
        val sut = Util(mainCoroutineRule.testDispatcher)
        val result = sut.getUserName()
        assertEquals(result, "CoroutineTesting")
    }


    @Test
    fun `test getUserName`() = runTest {
        val util = Util(mainCoroutineRule.testDispatcher)
        val userName = util.getUserName()
        assertEquals("CoroutineTesting", userName)
    }

    @Test
    fun `get User With Custom Coroutine Scope`() = runTest {
        val util = Util(mainCoroutineRule.testDispatcher)
        val user = util.getUser()
        assertEquals("User - CoroutineTesting", user)
    }

    @Test
    fun `get Address With Custom Coroutine Scope`() = runTest {
        val util = Util(mainCoroutineRule.testDispatcher)
        val user = util.getAddress()
        assertEquals("Address", user)
    }

    @Test
    fun `check if coroutine is executed or not`() = runTest {
        val util = Util(mainCoroutineRule.testDispatcher)
        util.getAddressDetails()
        mainCoroutineRule.testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(true, util.globalArg)
    }

}
