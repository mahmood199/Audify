package com.example.audify

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(value = Parameterized::class)
class ParameterizedSampleTest(
    val input: String,
    val expectedValue: Boolean
) {

    @Test
    fun test() {
        val sample = Sample()
        val result = sample.isPalindrome(input = input)
        assertEquals(result, expectedValue)
    }

    companion object {

        @JvmStatic
        @Parameters(name = "{index} : {0} is palindrome - {1}")
        fun data(): List<Array<Any>> {
            return listOf(
                arrayOf("Hellow", false),
                arrayOf("level", true),
                arrayOf("m", true),
                arrayOf("", true),
            )
        }
    }

}