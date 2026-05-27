package com.example.company_verification

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class CompanyVerificationApplication

fun main(args: Array<String>) {
	runApplication<CompanyVerificationApplication>(*args)
}
