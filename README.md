# 🏢 Company Verification Service

A backend microservice for verifying company data via multiple third-party providers, 
with intelligent fallback logic, caching, and persistent verification storage.

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat&logo=kotlin&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=flat&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)

## 🔍 What it does
- Queries company data from Free and Premium third-party providers
- Automatically falls back to Premium if Free service is unavailable (503)
- Filters and returns only **active companies**
- Stores all verifications in PostgreSQL with a unique verification ID
- Uses **Caffeine Cache** for performance optimization

## 🛠 Technologies
- Kotlin
- Spring Boot 3.5.14
- PostgreSQL 17
- Spring Data JPA
- Caffeine Cache
- Docker & Docker Compose
- Swagger / OpenAPI

## ▶️ How to run

### With Docker
```bash
docker-compose up
```

### Locally
1. Clone the repository
2. Create a PostgreSQL database: `company_verification_db`
3. Set environment variable: `DB_PASSWORD`
4. Run: `./gradlew bootRun`

## 📡 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/free-third-party?query=` | Free provider (40% error rate simulation) |
| GET | `/premium-third-party?query=` | Premium provider (10% error rate simulation) |
| GET | `/backend-service?verificationId=&query=` | Smart fallback — Free → Premium |
| GET | `/verifications/{verificationId}` | Retrieve stored verification by ID |
