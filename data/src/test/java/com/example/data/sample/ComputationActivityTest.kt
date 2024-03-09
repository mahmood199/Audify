package com.example.data.sample

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InOrder
import org.mockito.Mock
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.never
import org.mockito.Mockito.only
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ComputationActivityTest {

    @Mock
    public lateinit var operators: Operations
    public lateinit var computationActivity: ComputationActivity


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        computationActivity = ComputationActivity(operators)
    }

    @Test
    fun givenValidInput_getAddition_shouldCallAddOperator() {
        val x = 5
        val y = 10
        computationActivity.getAddition(x, y)
        verify(operators).add(x, y)
        verify(operators, only()).add(x, y)
    }

    @Test
    fun givenValidInput_getSubtraction_shouldCallSubtractOperator() {
        val x = 5
        val y = 10
        computationActivity.getSubtraction(y, x)
        verify(operators, times(1)).subtract(y, x)

        val inOrder = inOrder(operators)
        inOrder.verify(operators).subtract(y, x)
        inOrder.verify(operators).domeSomeMoreWork()
    }

    @Test
    fun givenValidInput_getMultiplication_shouldCallMultiplicationOperator() {
        val x = 5
        val y = 10
        computationActivity.getMultiplication(y, x)
        verify(operators).multiply(y, x)
    }

    @Test
    fun givenValidInput_getDivision_shouldCallDivisionOperator() {
        val x = 5
        val y = 10
        computationActivity.getMultiplication(y, x)
        verify(operators).multiply(y, x)
    }

    @After
    fun tearDown() {
    }
}