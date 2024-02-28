package com.example.audify.password

import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(value = Parameterized::class)
class StringReversalTest(
    private val input: String,
    private val output: String
) {

    lateinit var stringReversal: StringReversal

    @Before
    fun setUp() {
        stringReversal = StringReversal()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun reverse() {
        val result = stringReversal.reverse(input)
        assertEquals(result, output)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index} : {0} is valid password - {1}")
        fun data(): List<Array<Any?>> {
            return listOf(
                arrayOf("Hellow", "Hellow".reversed()),
                arrayOf("level", "level".reversed()),
                arrayOf("m", "m".reversed()),
                arrayOf("200", "200".reversed()),
                arrayOf("@@@", "@@@".reversed()),
                arrayOf("Mahmood@2000", "Mahmood@2000".reversed()),
            )
        }
    }

}