package com.zeezaglobal.digitalprescription.Utils

import android.util.Patterns
import com.zeezaglobal.digitalprescription.Validation.RegisterErrors
import com.zeezaglobal.digitalprescription.Validation.SucessMessage

object InputValidator {

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        // Password should be at least 6 characters, contain uppercase, lowercase, and a number
        val passwordPattern = "(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{6,}".toRegex()
        return password.matches(passwordPattern)
    }

    fun doPasswordsMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    // Returns a ValidationResult instead of Boolean
    fun validate(email: String, password: String, confirmPassword: String): String {
        if (!isValidEmail(email)) {
            return RegisterErrors.INVALID_EMAIL
        }
        if (!isValidPassword(password)) {
            return  RegisterErrors.WEAK_PASSWORD
        }
        if (!doPasswordsMatch(password, confirmPassword)) {
            return RegisterErrors.PASSWORDS_DO_NOT_MATCH
        }
        return SucessMessage.OK
    }
}