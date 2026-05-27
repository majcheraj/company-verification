package com.example.company_verification

object ValidationUtils {
    fun validateQuery(query: String) {
        if (query.isBlank()) {
            throw IllegalArgumentException(AppConstants.QUERY_BLANK_ERROR)
        }
    }

    fun validateVerificationId(verificationId: String) {
        if (verificationId.isBlank()) {
            throw IllegalArgumentException(AppConstants.VERIFICATION_ID_BLANK_ERROR)
        }
    }
}