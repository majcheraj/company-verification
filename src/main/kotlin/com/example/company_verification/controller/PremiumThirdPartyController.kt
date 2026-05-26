package com.example.company_verification.controller

import com.example.company_verification.service.PremiumThirdPartyService
import org.springframework.http.HttpStatus
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
        return try {
            val results = premiumThirdPartyService.searchByQuery(query)
            ResponseEntity.ok(results)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("503 Service Unavailable")
        }
    }
}