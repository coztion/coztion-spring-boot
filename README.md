# Coztion Server

Admin API server for Coztion, built with Spring Boot, MyBatis and MySQL.

## Tech Stack

| Category     | Library                                             |
| ------------ | --------------------------------------------------- |
| Framework    | Spring Boot 4.0 (Web MVC, Security, AOP, Validation) |
| Language     | Java 25                                             |
| Build        | Gradle 9.6                                          |
| Database     | MySQL, Flyway                                       |
| Persistence  | MyBatis 4, PageHelper                               |
| Cache        | Redis                                               |
| Storage      | AWS S3 (LocalStack for local)                       |
| Auth         | JWT (jjwt) with cookie-based access/refresh tokens  |
| API Docs     | springdoc-openapi (Swagger UI)                      |
| Code Quality | Spotless (Palantir Java Format)                     |

## Requirements

- JDK 25
- Docker

## Getting Started

```bash
docker compose up -d
./gradlew bootRun --args='--spring.profiles.active=local'
```

The server runs on `http://localhost:8080`.

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- Flyway creates tables and inserts mock data on startup.
- Default accounts: `system`, `admin`, `coztion`

## Environment

| Profile | Config File             | Description                            |
| ------- | ----------------------- | -------------------------------------- |
| `local` | `application-local.yml` | Local development with Docker services |
| `dev`   | `application-dev.yml`   | Development server                     |

| Variable                           | Description                              |
| ---------------------------------- | ---------------------------------------- |
| `SPRING_SECURITY_JWT_SECRET_VALUE` | Secret key for JWT signing               |
| `FLYWAY_CLEAN_ON_ERROR`            | Clean database on migration error (local) |

## Scripts

| Script                    | Description             |
| ------------------------- | ----------------------- |
| `./gradlew bootRun`       | Run the server          |
| `./gradlew build`         | Build the project       |
| `./gradlew test`          | Run tests               |
| `./gradlew spotlessApply` | Format with Spotless    |
| `./gradlew spotlessCheck` | Check formatting        |
