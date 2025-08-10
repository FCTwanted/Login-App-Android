package com.dam.p1_alten_felixcorraltejero

object Validator {

    private const val PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{6,8}\$"

    fun isPasswordValid(password: String): Boolean {
        return password.matches(PASSWORD_REGEX.toRegex())
    }

    fun areFieldsFilled(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }
}