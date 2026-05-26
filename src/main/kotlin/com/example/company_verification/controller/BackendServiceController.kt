package com.example.company_verification.controller

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
        return try {
            val result = backendService.processRequest(verificationId, query)
            ResponseEntity.ok(result)
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body("Something went wrong: ${e.message}")
        }
    }
}