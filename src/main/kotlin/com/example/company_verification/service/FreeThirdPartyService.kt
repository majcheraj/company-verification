package com.example.company_verification.service

import com.example.company_verification.AppConstants
import com.example.company_verification.model.FreeCompany
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class FreeThirdPartyService {

    private val logger = LoggerFactory.getLogger(FreeThirdPartyService::class.java)

    private val companies: List<FreeCompany> by lazy {
        val mapper = jacksonObjectMapper()
        val resource = ClassPathResource(AppConstants.FREE_SERVICE_JSON)
        mapper.readValue(resource.inputStream)
    }

    @Cacheable(value = ["freeCompanies"], key = "#query")
    fun searchByQuery(query: String): List<FreeCompany> {
        logger.info("FREE service called with query: '$query'")
        if (Math.random() < 0.4) {
            throw RuntimeException(AppConstants.FREE_SERVICE_UNAVAILABLE)
        }
        val results = companies.filter { it.cin.contains(query, ignoreCase = true) }
        logger.info("FREE service found ${results.size} results for query: '$query'")
        return results
    }
}