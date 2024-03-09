package com.example.data.sample

class ComputationActivity(private val operations: Operations) {

    fun getAddition(x: Int, y: Int): Int {
        return operations.add(x, y)
    }

    fun getSubtraction(x: Int, y: Int): Int {
        val result = operations.subtract(x, y)
        operations.domeSomeMoreWork()
        return result
    }

    fun getMultiplication(x: Int, y: Int): Int = operations.multiply(x, y)

    fun getDivision(x: Int, y: Int): Int = operations.divide(x, y)

}