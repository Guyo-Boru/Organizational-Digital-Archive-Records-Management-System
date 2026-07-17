# Digital Archive & Records Management System — Backend

Spring Boot 3 / Java 21 backend for the Digital Archive & Records Management System.
Confirmed working end-to-end: Postgres → Flyway → JPA → Keycloak auth → REST API.

## Stack
- Java 21
- Spring Boot 3.3.4
- Spring Data JPA (Hibernate 6.5)
- PostgreSQL 16
- Flyway (schema migrations)
- Keycloak 25 (authentication/authorization)
- Lombok
- Maven

## What's built so far

### Database layer
- 8 JPA entities in `domain/entity/`, matching the real schema exactly:
  Department, Category, AppUser, Document, DocumentVersion, DocumentNote,
  DocumentWorkflowHistory, AuditLog
- **All primary keys are `UUID`**, generated via `gen_random_uuid()` in Postgres
  (not auto-increment `bigint`) — this is a deliberate schema decision, not a default
- 5 enums in `domain/enums/` mapped via Hibernate's `NAMED_ENUM` to match
  Postgres enum types exactly (document_status, classification_level, note_type,
  audit_action, resource_type)
- 8 repositories in `repository/`, one per entity, with custom finder methods
  (full-text search on documents, sorted history lookups, etc.)
- 6 Flyway migrations in `src/main/resources/db/migration/` — schema, seed data,
  indexes, views, functions, triggers

### Security
- Keycloak realm `digital-archive` auto-provisioned via
  `keycloak/realm-export.json` on container startup
- 5 roles: ADMIN, ARCHIVIST, MANAGER, DEPT_USER, VIEWER
- 2 test users: `admin.user` / `password123` (ADMIN), `dept.user` / `password123` (DEPT_USER)
- `SecurityConfig.java` — validates Keycloak JWTs on every request, maps realm
  roles to Spring authorities (`ROLE_ADMIN`, etc.)

### API — confirmed working
- `DepartmentController`, `CategoryController`, `UserController` — full
  GET/POST/PUT/DELETE, all requiring a valid Bearer token

## Prerequisites
- JDK 21 (confirm with `java -version` — must show 21.x, not 25/26)
- Maven
- Docker + Docker Compose

## Run it

### 1. Start Postgres + Keycloak
```bash
docker compose up -d
docker compose ps   # confirm all 3 containers show healthy/running
```

### 2. Run the app
```bash
mvn spring-boot:run
```
 ### 3. Get a token (separate terminal)
```bash
curl -X POST http://localhost:8081/realms/digital-archive/protocol/openid-connect/token ^
 -d "client_id=archive-frontend" -d "grant_type=password" ^
 -d "username=admin.user" -d "password=password123"
```
Tokens expire in 5 minutes — get a fresh one if you get a 401.

### 4. Call the API
```bash
curl http://localhost:8080/api/departments -H "Authorization: Bearer <token>"
```
Returns real seeded department data (Administration, Finance, HR, ICT,
Procurement, Legal) as JSON.

### 5. Stop everything
```bash
docker compose down
```
Add `-v` to also wipe database data (useful if Flyway checksums ever conflict
during development — see Troubleshooting below).

## Keycloak admin console
`http://localhost:8081` — login `admin` / `admin` → realm `digital-archive`

## Troubleshooting notes (things that came up during setup)
- **Wrong JDK picked up**: if you see Lombok/`javac` internal errors, check
  `java -version` and `mvn -version` both show 21 — VS Code's terminal profile
  and `JAVA_HOME` can silently point at a different installed JDK.
- **Flyway checksum mismatch**: happens if a migration file is edited after
  already being applied once. In dev, reset with `docker compose down -v`
  then `docker compose up -d`.
- **Hibernate schema-validation errors on startup**: means a JPA entity field
  doesn't match the real column type in Postgres (common culprits: UUID vs
  bigint, CHAR vs VARCHAR, JSONB, INET). Compare the entity's `@Column`
  annotations against the actual `CREATE TABLE` statement in
  `V1__init_schema.sql`.

## Next up (Week 2)
- Document CRUD + service layer (classification, soft delete, reference
  number auto-generation via trigger)
- Document version upload/download with SHA-256 checksum
- Workflow engine: DRAFT → SUBMITTED → UNDER_REVIEW → APPROVED/REJECTED → ARCHIVED
- Audit logging wired into every significant action