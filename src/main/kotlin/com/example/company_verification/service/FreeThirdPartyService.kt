package com.example.company_verification.service

import com.example.company_verification.AppConstants
import com.example.company_verification.model.FreeCompany
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class FreeThirdPartyService {

    private val companies: List<FreeCompany> by lazy {
        val mapper = jacksonObjectMapper()
        val resource = ClassPathResource(AppConstants.FREE_SERVICE_JSON)
        mapper.readValue(resource.inputStream)
    }

    fun searchByQuery(query: String): List<FreeCompany> {
        if (Math.random() < 0.4) {
            throw RuntimeException(AppConstants.FREE_SERVICE_UNAVAILABLE)
        }
        return companies.filter { it.cin.contains(query, ignoreCase = true) }
    }
}