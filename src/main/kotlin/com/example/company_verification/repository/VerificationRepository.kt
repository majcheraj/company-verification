package com.example.company_verification.repository

import com.example.company_verification.model.Verification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VerificationRepository : JpaRepository<Verification, String>