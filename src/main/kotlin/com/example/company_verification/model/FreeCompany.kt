package com.example.company_verification.model

import com.fasterxml.jackson.annotation.JsonProperty

data class FreeCompany(
        val cin: String,
        val name: String,
        @JsonProperty("registration_date")
        val registrationDate: String,
        val address: String,
        @JsonProperty("is_active")
        val isActive: Boolean
)