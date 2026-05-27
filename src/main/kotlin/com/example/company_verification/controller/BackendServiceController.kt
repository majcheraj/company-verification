package com.example.company_verification.controller

import com.example.company_verification.AppConstants
import com.example.company_verification.ValidationUtils
import com.example.company_verification.service.BackendService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class BackendServiceController(
        private val backendService: BackendService
) {

    @GetMapping("/backend-service")
    fun process(
            @RequestParam verificationId: String,
            @RequestParam query: String
    ): ResponseEntity<Any> {
        ValidationUtils.validateVerificationId(verificationId)
        ValidationUtils.validateQuery(query)
        return try {
            val result = backendService.processRequest(verificationId, query)
            ResponseEntity.ok(result)
        } catch (e: IllegalArgumentException) {
            throw e
        } catch (e: Exception) {
            throw Exception("Something went wrong: ${e.message}")
        }
    }
}