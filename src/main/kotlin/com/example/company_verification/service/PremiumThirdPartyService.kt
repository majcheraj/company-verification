package com.example.company_verification.service

import com.example.company_verification.model.PremiumCompany
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class PremiumThirdPartyService {

    private val companies: List<PremiumCompany> by lazy {
        val mapper = jacksonObjectMapper()
        val resource = ClassPathResource("premium_service_companies-1.json")
        mapper.readValue(resource.inputStream)
    }

    fun searchByQuery(query: String): List<PremiumCompany> {
        if (Math.random() < 0.1) {
            throw RuntimeException("503 Service Unavailable")
        }
        return companies.filter { it.companyIdentificationNumber.contains(query, ignoreCase = true) }
    }
}