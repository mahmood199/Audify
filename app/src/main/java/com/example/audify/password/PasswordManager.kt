package com.example.audify.password

class PasswordManager {

    fun validatePassword(password: String?): Boolean {
        if (password.isNullOrEmpty()) return false

        if (password.length !in 6..15) return false

        val capitalLetter = Regex("[A-Z]").containsMatchIn(password)
        val smallLetter = Regex("[a-z]").containsMatchIn(password)
        val specialCharacter = Regex("[!@#$%^&*()-+=]").containsMatchIn(password)
        val numbers = Regex("\\d").containsMatchIn(password)

        return capitalLetter && smallLetter && specialCharacter && numbers
    }

}