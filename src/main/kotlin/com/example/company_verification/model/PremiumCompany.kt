package com.example.company_verification.model

import com.fasterxml.jackson.annotation.JsonProperty

data class PremiumCompany(
        val companyIdentificationNumber: String,
        val companyName: String,
        val registrationDate: String,
        @JsonProperty("companyFullAddress")
        val fullAddress: String,
        val isActive: Boolean
)