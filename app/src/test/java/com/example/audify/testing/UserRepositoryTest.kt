package com.example.audify.testing

import com.example.audify.MainCoroutineRule
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun getUsers() {

    }

    @Test
    fun doSomeTests() = runTest(mainCoroutineRule.testDispatcher) {
        val userRepo = UserRepository()
        val scope = CoroutineScope(mainCoroutineRule.testDispatcher)

        scope.launch {
            delay(1000)
            userRepo.register("Alice")
            scope.launch {
                userRepo.register("Bob")
            }
        }

        mainCoroutineRule.testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(2, userRepo.users.size)
    }

    @Test
    fun doSomeTests2() = runTest(mainCoroutineRule.testDispatcher) {
        val userRepo = UserRepository()
        val scope = CoroutineScope(mainCoroutineRule.testDispatcher)

        scope.launch {
            delay(1000)
            userRepo.register("Alice")
            scope.launch {
                userRepo.register("Bob")
            }
        }

        assertNotEquals(2, userRepo.users.size)
    }

    @Test
    fun doSomeTests3() = runTest(mainCoroutineRule.testDispatcher) {
        val userRepo = UserRepository()
        val scope = CoroutineScope(mainCoroutineRule.testDispatcher)

        scope.launch {
            delay(1000)

            userRepo.register("Alice")
            delay(1000)
            userRepo.register("Jim")
            scope.launch {
                userRepo.register("Bob")
            }
        }

        /*
                Fails because only one(1) item is added now
                mainCoroutineRule.testDispatcher.scheduler.advanceTimeBy(1001)
        */
        mainCoroutineRule.testDispatcher.scheduler.advanceTimeBy(1000)
        /*
                Succeeds because only 2 items are added by now
                mainCoroutineRule.testDispatcher.scheduler.advanceTimeBy(1001)
                and later 3
        */
        mainCoroutineRule.testDispatcher.scheduler.advanceTimeBy(1001)


        assertEquals(3, userRepo.users.size)
    }

    @Test
    fun register() {
    }
}