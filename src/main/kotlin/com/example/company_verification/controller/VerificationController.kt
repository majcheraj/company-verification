package com.example.company_verification.controller

import com.example.company_verification.repository.VerificationRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class VerificationController(
        private val verificationRepository: VerificationRepository
) {

    @GetMapping("/verifications/{verificationId}")
    fun getVerification(@PathVariable verificationId: String): ResponseEntity<Any> {
        val verification = verificationRepository.findById(verificationId)
        return if (verification.isPresent) {
            ResponseEntity.ok(verification.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/verifications")
    fun getAllVerifications(
            @RequestParam(defaultValue = "0") page: Int,
            @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Any> {
        val pageable = PageRequest.of(page, size, Sort.by("timestamp").descending())
        val verifications = verificationRepository.findAll(pageable)
        return ResponseEntity.ok(mapOf(
                "content" to verifications.content,
                "currentPage" to verifications.number,
                "totalItems" to verifications.totalElements,
                "totalPages" to verifications.totalPages
        ))
    }
}