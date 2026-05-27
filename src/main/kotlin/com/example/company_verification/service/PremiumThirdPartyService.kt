package com.example.company_verification.service

import kotlin.random.Random
import com.example.company_verification.AppConstants
import com.example.company_verification.model.PremiumCompany
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class PremiumThirdPartyService {

    private val logger = LoggerFactory.getLogger(PremiumThirdPartyService::class.java)

    private val companies: List<PremiumCompany> by lazy {
        val mapper = jacksonObjectMapper()
        val resource = ClassPathResource(AppConstants.PREMIUM_SERVICE_JSON)
        mapper.readValue(resource.inputStream)
    }
    @Cacheable(value = ["premiumCompanies"], key = "#query")
    fun searchByQuery(query: String): List<PremiumCompany> {
        logger.info("PREMIUM service called with query: '$query'")
        if (Random.nextDouble() < 0.1) {
            throw RuntimeException(AppConstants.PREMIUM_SERVICE_UNAVAILABLE)
        }
        val results = companies.filter { it.companyIdentificationNumber.contains(query, ignoreCase = true) }
        logger.info("PREMIUM service found ${results.size} results for query: '$query'")
        return results
    }
}