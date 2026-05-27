package com.example.company_verification

import java.util.UUID

object ValidationUtils {

    private val UUID_REGEX = Regex(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"
    )

    fun validateQuery(query: String) {
        if (query.isBlank()) {
            throw IllegalArgumentException(AppConstants.QUERY_BLANK_ERROR)
        }
    }

    fun validateVerificationId(verificationId: String) {
        if (verificationId.isBlank()) {
            throw IllegalArgumentException(AppConstants.VERIFICATION_ID_BLANK_ERROR)
        }
        if (!UUID_REGEX.matches(verificationId)) {
            throw IllegalArgumentException(AppConstants.VERIFICATION_ID_INVALID_FORMAT)
        }
    }
}