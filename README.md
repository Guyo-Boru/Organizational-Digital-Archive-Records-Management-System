 # Digital Archive & Records Management System — Backend

This repository is a Spring Boot backend for a digital archive / records management system.
It includes entity mapping, repository interfaces, application configuration, and Docker setup for PostgreSQL and Keycloak.

## Project structure

- `pom.xml`
  - Spring Boot 3.3.4
  - Java 21
  - `spring-boot-starter-web`
  - `spring-boot-starter-data-jpa`
  - PostgreSQL JDBC driver
  - Flyway
  - Lombok
  - Spring Boot test dependencies

- `docker-compose.yml`
  - `postgres-app` for the application database
  - `postgres-keycloak` for Keycloak's own DB
  - `keycloak` running with `--import-realm`

- `src/main/java/com/digitalarchive/DigitalArchiveApplication.java`
  - Spring Boot application entry point

- `src/main/java/com/digitalarchive/domain/entity/`
  - JPA entities: `AppUser`, `AuditLog`, `Category`, `Department`, `Document`, `DocumentNote`, `DocumentVersion`, `DocumentWorkflowHistory`

- `src/main/java/com/digitalarchive/domain/enums/`
  - domain enums: `AuditAction`, `ClassificationLevel`, `DocumentStatus`, `NoteType`, `ResourceType`

- `src/main/java/com/digitalarchive/repository/`
  - Spring Data JPA repositories for each entity

- `src/main/resources/application.yml`
  - database connection settings
  - JPA validation mode
  - Flyway configuration
  - server port
  - file storage location

- `src/main/resources/db/migration/`
  - placeholder README for Flyway migrations

- `keycloak/realm-export.json`
  - Keycloak realm import file used by Docker Compose

## What is implemented today

- Project scaffolding and Spring Boot startup
- Entity classes and JPA mappings
- Repository interfaces for each entity
- Flyway database migration support
- Docker Compose environment for application DB and Keycloak

## What is not implemented yet

- REST controllers and API endpoints
- Spring Security / JWT validation for Keycloak
- service layer business logic
- production-ready deployment configuration

## Configuration notes

`application.yml` uses environment variables with defaults:

- `DB_HOST` (default: `localhost`)
- `DB_PORT` (default: `5432`)
- `DB_NAME` (default: `digital_archive`)
- `DB_USER` (default: `archive_app`)
- `DB_PASSWORD` (default: `archive_app_password`)
- `SERVER_PORT` (default: `8080`)
- `FILE_STORAGE_LOCATION` (default: `./uploads`)

Flyway is configured to validate schema and load migrations from `classpath:db/migration`.

## Run the application

### 1. Start Docker services
```bash
docker compose up -d
docker compose logs -f keycloak
```
Wait for Keycloak to finish importing the realm.

### 2. Add Flyway migrations
Copy your migration files (`V1__init_schema.sql`, `V2__*.sql`, ..., `V6__*.sql`) into:

```text
src/main/resources/db/migration/
```

### 3. Run the app
```bash
mvn spring-boot:run
```

The application should start on `http://localhost:8080` and run Flyway migrations on startup.

### 4. Verify Keycloak
Open `http://localhost:8081` and log in as:

- username: `admin`
- password: `admin`

Then inspect the imported `digital-archive` realm.

## Notes

- The backend currently has repositories and entities, but no HTTP API layers.
- Keycloak is configured by Docker Compose, but Spring Boot does not yet enforce Keycloak authentication.
- The `domain/migration` package exists in the source tree, but the main Flyway migration scripts belong under `src/main/resources/db/migration/`.

# Organizational-Digital-Archive-Records-Management-System
full stack dev.t 
 
