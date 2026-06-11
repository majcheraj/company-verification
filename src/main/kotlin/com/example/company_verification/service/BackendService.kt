package com.example.company_verification.service

import com.example.company_verification.AppConstants
import com.example.company_verification.model.Verification
import com.example.company_verification.repository.VerificationRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class BackendService(
        private val freeService: FreeThirdPartyService,
        private val premiumService: PremiumThirdPartyService,
        private val verificationRepository: VerificationRepository
) {
    private val logger = LoggerFactory.getLogger(BackendService::class.java)
    private val mapper = jacksonObjectMapper()
    @Transactional
    fun processRequest(verificationId: String, query: String): Map<String, Any?> {
        logger.info("Backend service called with verificationId: '$verificationId' and query: '$query'")
        var source = AppConstants.FREE_SERVICE
        var activeCompanies: List<Map<String, Any?>> = emptyList()
        var status = "SUCCESS"

        try {
            val freeResults = freeService.searchByQuery(query)
            activeCompanies = freeResults
                    .filter { it.isActive }
                    .map { mapOf(
                            "cin" to it.cin,
                            "name" to it.name,
                            "registration_date" to it.registrationDate,
                            "address" to it.address,
                            "is_active" to it.isActive
                    )}
        } catch (e: Exception) {
            logger.warn("FREE service failed, falling back to PREMIUM service")
            source = AppConstants.PREMIUM_SERVICE
        }

        if (activeCompanies.isEmpty()) {
            source = AppConstants.PREMIUM_SERVICE
            try {
                val premiumResults = premiumService.searchByQuery(query)
                activeCompanies = premiumResults
                        .filter { it.isActive }
                        .map { mapOf(
                                "companyIdentificationNumber" to it.companyIdentificationNumber,
                                "companyName" to it.companyName,
                                "registrationDate" to it.registrationDate,
                                "companyFullAddress" to it.fullAddress,
                                "isActive" to it.isActive
                        )}
            } catch (e: Exception) {
                logger.error("PREMIUM service also failed, both services are unavailable")
                status = "THIRD_PARTIES_DOWN"
            }
        }

        val result: Map<String, Any?> = when {
            status == "THIRD_PARTIES_DOWN" -> mapOf("status" to AppConstants.BOTH_SERVICES_UNAVAILABLE)
            activeCompanies.isEmpty() -> mapOf("status" to AppConstants.NO_RESULTS_FOUND)
            activeCompanies.size == 1 -> activeCompanies.first()
            else -> activeCompanies.first().toMutableMap().apply {
                put("otherResults", activeCompanies.drop(1))
            }
        }

        logger.info("Backend service returning result from '$source' for verificationId: '$verificationId'")

        val existingVerification = verificationRepository.findById(verificationId)
        if (existingVerification.isPresent) {
            logger.info("Verification with id '$verificationId' already exists, returning existing result")
            return mapOf(
                    "verificationId" to existingVerification.get().verificationId,
                    "query" to existingVerification.get().queryText,
                    "result" to existingVerification.get().result,
                    "message" to "This verificationId was already used for query '${existingVerification.get().queryText}'. Please generate a new verificationId for a new search."
            )
        }

        val verification = Verification(
                verificationId = verificationId,
                queryText = query,
                timestamp = LocalDateTime.now(),
                result = mapper.writeValueAsString(result),
                source = source
        )
        verificationRepository.save(verification)

        return mapOf(
                "verificationId" to verificationId,
                "query" to query,
                "result" to result
        )
    }
}