package com.example.company_verification.controller

import com.example.company_verification.AppConstants
import com.example.company_verification.ValidationUtils
import com.example.company_verification.model.ServiceUnavailableException
import com.example.company_verification.service.PremiumThirdPartyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PremiumThirdPartyController(
        private val premiumThirdPartyService: PremiumThirdPartyService
) {

    @GetMapping("/premium-third-party")
    fun search(@RequestParam query: String): ResponseEntity<Any> {
        ValidationUtils.validateQuery(query)
        return try {
            val results = premiumThirdPartyService.searchByQuery(query)
            ResponseEntity.ok(results)
        } catch (e: IllegalArgumentException) {
            throw e
        } catch (e: Exception) {
            throw ServiceUnavailableException(AppConstants.PREMIUM_SERVICE_UNAVAILABLE)
        }
    }
}