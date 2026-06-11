package com.example.company_verification.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "verifications")
data class Verification(
        @Id
        val verificationId: String,

        val queryText: String,

        val timestamp: LocalDateTime = LocalDateTime.now(),

        @Column(columnDefinition = "TEXT")
        val result: String,

        val source: String,

        @Version
        val version: Long = 0,

        val expiresAt: LocalDateTime = LocalDateTime.now().plusHours(24)
)