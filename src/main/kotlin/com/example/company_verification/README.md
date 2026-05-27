# Company Verification Service

Backend service built with Kotlin, Spring Boot and PostgreSQL.

## Technologies
- Kotlin
- Spring Boot 3.5.14
- PostgreSQL 17
- Spring Data JPA
- Caffeine Cache

## Prerequisites
- Java 19
- PostgreSQL 17
- Environment variable `DB_PASSWORD` set to your PostgreSQL password

## How to run
1. Clone the repository
2. Create a PostgreSQL database named `company_verification_db`
3. Set environment variable `DB_PASSWORD`
4. Run the application from IntelliJ or with `./gradlew bootRun`

## API Endpoints

### Free Third Party Service
`GET /free-third-party?query={query}`
- Returns companies whose CIN contains the query string
- Simulates 503 error 40% of the time

### Premium Third Party Service
`GET /premium-third-party?query={query}`
- Returns companies whose CIN contains the query string
- Simulates 503 error 10% of the time

### Backend Service
`GET /backend-service?verificationId={guid}&query={query}`
- Calls Free service first, falls back to Premium if unavailable
- Returns only active companies
- Stores verification in database

### Get Verification
`GET /verifications/{verificationId}`
- Returns stored verification data by ID