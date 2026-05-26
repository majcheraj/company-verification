package com.example.company_verification.service

import com.example.company_verification.model.FreeCompany
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class FreeThirdPartyService {

    private val companies: List<FreeCompany> by lazy {
        val mapper = jacksonObjectMapper()
        val resource = ClassPathResource("free_service_companies-1.json")
        mapper.readValue(resource.inputStream)
    }

    fun searchByQuery(query: String): List<FreeCompany> {
        if (Math.random() < 0.4) {
            throw RuntimeException("503 Service Unavailable")
        }
        return companies.filter { it.cin.contains(query, ignoreCase = true) }
    }
}