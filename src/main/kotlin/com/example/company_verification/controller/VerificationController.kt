package com.example.company_verification.controller

import com.example.company_verification.repository.VerificationRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
}