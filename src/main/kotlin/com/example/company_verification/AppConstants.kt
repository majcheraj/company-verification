package com.example.company_verification

object AppConstants {
    // Service names
    const val FREE_SERVICE = "FREE"
    const val PREMIUM_SERVICE = "PREMIUM"

    // Error messages
    const val QUERY_BLANK_ERROR = "Query parameter cannot be empty"
    const val VERIFICATION_ID_BLANK_ERROR = "verificationId cannot be empty"
    const val FREE_SERVICE_UNAVAILABLE = "Free third party service is unavailable"
    const val PREMIUM_SERVICE_UNAVAILABLE = "Premium third party service is unavailable"
    const val BOTH_SERVICES_UNAVAILABLE = "Both third party services are unavailable"
    const val NO_RESULTS_FOUND = "No results found"
    const val UNEXPECTED_ERROR = "An unexpected error occurred"

    // JSON file names
    const val FREE_SERVICE_JSON = "free_service_companies-1.json"
    const val PREMIUM_SERVICE_JSON = "premium_service_companies-1.json"
    const val VERIFICATION_ID_INVALID_FORMAT = "verificationId must be a valid GUID format (e.g. 123e4567-e89b-12d3-a456-426614174000)"
    const val VERIFICATION_EXPIRED = "Verification has expired, fetching fresh data"
}