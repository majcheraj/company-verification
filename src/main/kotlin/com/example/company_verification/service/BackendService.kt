package com.example.company_verification.service

import com.example.company_verification.AppConstants
import com.example.company_verification.model.Verification
import com.example.company_verification.repository.VerificationRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BackendService(
        private val freeService: FreeThirdPartyService,
        private val premiumService: PremiumThirdPartyService,
        private val verificationRepository: VerificationRepository
) {

    private val mapper = jacksonObjectMapper()

    fun processRequest(verificationId: String, query: String): Map<String, Any?> {
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