package com.example.audify

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class SampleTest {

    lateinit var sample: Sample

    @Before
    fun setup() {
        sample = Sample()
    }

    @Test
    fun isPalindrome_input_hello_expect_false() {
        // Arrange
        // Act
        val result = sample.isPalindrome("Hello")
        // Assert
        Assert.assertEquals(false, result)
    }

    @Test
    fun isPalindrome_input_level_expect_true() {
        // Arrange
        // Act
        val result = sample.isPalindrome("level")
        // Assert
        Assert.assertEquals(true, result)
    }

    @After
    fun tearDown() {
        println("After running the test")
    }

}