package com.example.audify.password

class StringReversal {

    fun reverse(input: String): String {
        val charArray = input.toCharArray()
        var i = 0
        var j = input.length - 1

        while (i < j) {
            val temp = charArray[i]
            charArray[i] = charArray[j]
            charArray[j] = temp
            i++
            j--
        }

        return String(charArray)
    }
}
