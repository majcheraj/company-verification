package com.example.company_verification.service

import com.example.company_verification.AppConstants
import com.example.company_verification.model.PremiumCompany
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class PremiumThirdPartyService {

    private val companies: List<PremiumCompany> by lazy {
        val mapper = jacksonObjectMapper()
        val resource = ClassPathResource(AppConstants.PREMIUM_SERVICE_JSON)
        mapper.readValue(resource.inputStream)
    }

    fun searchByQuery(query: String): List<PremiumCompany> {
        if (Math.random() < 0.1) {
            throw RuntimeException(AppConstants.PREMIUM_SERVICE_UNAVAILABLE)
        }
        return companies.filter { it.companyIdentificationNumber.contains(query, ignoreCase = true) }
    }
}