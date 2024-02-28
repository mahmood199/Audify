package com.example.audify.password

import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(value = Parameterized::class)
class PasswordManagerTest(
    private val input: String?,
    private val expectedValue: Boolean
) {

    lateinit var passwordManager: PasswordManager

    @Before
    fun setUp() {
        passwordManager = PasswordManager()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun validatePassword() {
        val result = passwordManager.validatePassword(input)
        assertEquals(result, expectedValue)
    }

    companion object {
        @JvmStatic
        @Parameters(name = "{index} : {0} is valid password - {1}")
        fun data(): List<Array<Any?>> {
            return listOf(
                arrayOf("Hellow", false),
                arrayOf("level", false),
                arrayOf("m", false),
                arrayOf("200", false),
                arrayOf("@@@", false),
                arrayOf(null, false),
                arrayOf("Mahmood@2000", true),
            )
        }
    }
}