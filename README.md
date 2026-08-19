Organizational Digital Archive & Records Management System

A secure, web-based digital archive and records management system designed to help organizations register, classify, store, manage, search, approve, track, and protect official documents.

The system is being developed as a full-stack application with a Java/Spring Boot backend, React frontend, PostgreSQL database, Keycloak-based authentication and authorization, and containerized deployment support.

📌 Project Overview

Many organizations still rely on physical documents, scattered digital files, and inefficient manual processes to manage official records.

This project provides a centralized digital archive where authorized users can:

Register official documents
Upload and manage digital document files
Organize documents by department and category
Apply document classification levels
Search and retrieve documents
Manage document versions
Track document workflow and status changes
Add notes to documents
Maintain an audit trail of important system activities
Control access using role-based authorization
Preserve document integrity using file checksums

The system is designed around the principle of secure, traceable, and structured document management.

🎯 Project Objectives

The main objectives of the system are to:

Digitize organizational document archiving.
Centralize official records in a structured system.
Improve document search and retrieval.
Maintain document version history.
Support document approval workflows.
Enforce secure access to sensitive documents.
Track important activities through audit logs.
Maintain document integrity through checksum verification.
Provide a maintainable and scalable full-stack architecture.
Provide a foundation for future deployment in real organizational environments.
🏗️ System Architecture

The application follows a layered architecture:

┌──────────────────────────────┐
│          React Frontend      │
│       User Interface         │
└──────────────┬───────────────┘
               │
               │ REST API
               ▼
┌──────────────────────────────┐
│       Spring Boot Backend    │
│                              │
│  Controllers                 │
│       ↓                      │
│  Services                    │
│       ↓                      │
│  Repositories                │
│       ↓                      │
│  JPA Entities                │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│       PostgreSQL Database    │
│                              │
│  Documents                   │
│  Versions                    │
│  Departments                 │
│  Categories                  │
│  Workflow History            │
│  Audit Logs                  │
└──────────────────────────────┘

          Authentication
               ▲
               │
        ┌──────┴──────┐
        │  Keycloak   │
        │             │
        │ Users       │
        │ Roles       │
        │ Tokens      │
        └─────────────┘
🧱 Technology Stack
Backend
Technology	Version / Role
Java	25.0.3
Spring Boot	4.1.0
Spring Framework	7.0.8
Spring Data JPA	Database persistence
Hibernate ORM	7.4.1.Final
Maven	Build and dependency management
Lombok	Boilerplate reduction
MapStruct	DTO/entity mapping
Flyway	Database migration management
HikariCP	JDBC connection pooling
Apache Tomcat	11.0.22
Database
Technology	Version / Role
PostgreSQL	18.4
PostgreSQL JDBC Driver	42.7.11
PostgreSQL pgcrypto	UUID and cryptographic functions
Frontend
Technology	Role
React	User interface
JavaScript	Frontend programming language
Vite	Frontend build tool
HTML5	Structure
CSS3	Styling
Security
Technology	Role
Keycloak	Identity and access management
OAuth 2.0 / OpenID Connect	Authentication protocol
JWT	Secure token-based communication
Role-Based Access Control	Authorization
Development and Deployment
Technology	Role
Git	Version control
GitHub	Remote repository
Docker	Containerization
Postman	API testing
VS Code	Primary development environment
📂 Project Structure

The project is organized as a modular monolithic application.

archive-system/
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/guyo/archive_system/
│   │   │   │       │
│   │   │   │       ├── ArchiveSystemApplication.java
│   │   │   │       │
│   │   │   │       ├── department/
│   │   │   │       │   ├── controller/
│   │   │   │       │   ├── dto/
│   │   │   │       │   ├── entity/
│   │   │   │       │   ├── mapper/
│   │   │   │       │   ├── repository/
│   │   │   │       │   └── service/
│   │   │   │       │
│   │   │   │       ├── category/
│   │   │   │       │
│   │   │   │       ├── document/
│   │   │   │       │
│   │   │   │       ├── documentversion/
│   │   │   │       │
│   │   │   │       ├── documentnote/
│   │   │   │       │
│   │   │   │       ├── workflow/
│   │   │   │       │
│   │   │   │       ├── audit/
│   │   │   │       │
│   │   │   │       ├── user/
│   │   │   │       │
│   │   │   │       └── common/
│   │   │   │
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       │
│   │   │       └── db/
│   │   │           └── migration/
│   │   │               ├── V1__init_schema.sql
│   │   │               ├── V2__seed_data.sql
│   │   │               ├── V3__create_indexes.sql
│   │   │               ├── V4__create_views.sql
│   │   │               ├── V5__...
│   │   │               └── V6__...
│   │   │
│   │   └── test/
│   │
│   └── pom.xml
│
├── frontend/
│   └── React application
│
├── docs/
│   ├── architecture/
│   ├── database/
│   ├── api/
│   └── deployment/
│
├── docker-compose.yml
├── README.md
└── .gitignore

The exact folder structure may evolve as implementation progresses, but the architectural boundaries should remain consistent.

🗄️ Database Design

The database is managed using Flyway migrations.

The current schema has successfully reached:

Flyway Schema Version: 6
Database: PostgreSQL 18.4
Schema: public
Status: Up to date

The application validates and executes database migrations automatically during startup.

Current startup confirmation:

Successfully validated 6 migrations
Current version of schema "public": 6
Schema "public" is up to date
No migration necessary
🧩 Core Domain Entities

The database is designed around the following core entities.

1. Departments

Represents organizational departments or units.

Example:

Human Resources
Finance
Information Technology
Administration

A department may be associated with multiple users and documents.

2. Categories

Provides structured classification of documents.

Examples:

Policies
Reports
Contracts
Financial Documents
Administrative Documents
Technical Documents

A category may contain multiple documents.

3. Users

The system stores user references associated with the identity management system.

The application does not duplicate authentication responsibilities.

Instead:

Keycloak
   │
   ├── User Identity
   ├── Password Management
   ├── Roles
   └── Access Tokens
          │
          ▼
Spring Boot Application
          │
          ▼
PostgreSQL

The database stores the user's Keycloak identifier (user_sub) where necessary for relationships and audit tracking.

4. Documents

The central entity of the system.

A document contains information such as:

Title
Description
Document number
Department
Category
Classification level
Current status
Creator
Current version
Timestamps

A document represents the logical record, while the actual file versions are managed separately.

5. Document Versions

A document may have multiple versions.

Example:

Document
   │
   ├── Version 1
   ├── Version 2
   └── Version 3 ← Current Version

Each version may contain:

Version number
File name
File type
File size
Storage path
SHA-256 checksum
Uploaded by
Upload timestamp

This allows the system to preserve historical versions rather than overwriting files.

6. Document Notes

Allows authorized users to add notes associated with documents.

Possible note types include:

COMMENT
REVIEW
REJECTION_REASON
APPROVAL_NOTE
GENERAL

Notes provide contextual information without modifying the original document.

7. Document Workflow History

Records changes in a document's workflow.

Example:

DRAFT
   ↓
SUBMITTED
   ↓
UNDER_REVIEW
   ↓
APPROVED

Or:

UNDER_REVIEW
   ↓
REJECTED
   ↓
DRAFT

Each transition can be tracked with:

Previous status
New status
User responsible
Timestamp
Optional explanation
8. Audit Logs

Audit logs provide accountability.

The system can record actions such as:

CREATE
VIEW
UPDATE
DELETE
DOWNLOAD
UPLOAD
APPROVE
REJECT
LOGIN
LOGOUT

The purpose is to answer:

Who did what, to which resource, and when?

This is especially important for official organizational records.

🔐 Security Model

Authentication and authorization are handled through Keycloak.

The application is designed to avoid implementing its own password and user authentication system.

The security flow is:

User
 │
 ▼
Keycloak Login
 │
 ▼
Access Token / JWT
 │
 ▼
React Frontend
 │
 ▼
Spring Boot REST API
 │
 ▼
Token Validation
 │
 ▼
Role-Based Authorization

Example roles may include:

ADMIN
ARCHIVIST
REVIEWER
DEPARTMENT_USER

The exact role model will be finalized according to the system's functional requirements.

### Keycloak role mapping

The API maps roles from both Keycloak realm roles (`realm_access.roles`) and
client roles for the `archive-system` client (`resource_access.archive-system.roles`)
to Spring Security authorities such as `ROLE_AUDITOR` and
`ROLE_ARCHIVE_OFFICER`. OAuth scopes are retained as `SCOPE_*` authorities.

Supported operational roles are `SUPER_ADMIN`, `ADMIN`, `ARCHIVE_OFFICER`,
`RECORDS_MANAGER`, `DEPARTMENT_MANAGER`, `REVIEWER`, and `AUDITOR`. Assign
these roles in Keycloak before testing protected workflow and audit endpoints.

📄 Document Classification

Documents may be assigned classification levels.

The current design supports classification concepts such as:

PUBLIC
INTERNAL
CONFIDENTIAL
RESTRICTED

Classification is separate from authentication.

For example:

Authentication:
    Who are you?

Authorization:
    What are you allowed to do?

Classification:
    How sensitive is this document?

The system combines these concepts to control document access appropriately.

🔄 Document Workflow

The document lifecycle is designed around controlled status transitions.

Example:

┌────────┐
│ DRAFT  │
└───┬────┘
    │
    ▼
┌───────────┐
│ SUBMITTED │
└─────┬─────┘
      │
      ▼
┌──────────────┐
│ UNDER_REVIEW │
└──────┬───────┘
       │
   ┌───┴────┐
   ▼        ▼
APPROVED  REJECTED
             │
             ▼
          DRAFT

The workflow history records each transition.

🔢 Document Versioning

The system uses version-based document management.

Example:

Annual Report
│
├── v1.0
├── v2.0
└── v3.0 ← Current

Previous versions remain available according to authorization rules.

Each version has a SHA-256 checksum.

File
 │
 ▼
SHA-256 Hash
 │
 ▼
Stored Checksum

This allows the system to detect whether a file has been altered.

🔍 Search and Retrieval

The system will support document retrieval based on relevant metadata.

Possible search parameters include:

Document title
Document number
Category
Department
Status
Classification level
Creation date
Upload date
Creator
Current version

The search layer will be implemented progressively without introducing unnecessary infrastructure.

The project deliberately avoids adding unnecessary technologies such as:

Redis
RabbitMQ
Microservices

The current scope is a modular monolith, which is more appropriate for the project timeline and requirements.

🧬 Database Migration Strategy

Database changes are managed using Flyway.

Example:

V1__init_schema.sql
V2__seed_data.sql
V3__create_indexes.sql
V4__create_views.sql
V5__additional_changes.sql
V6__additional_changes.sql

The rule is:

Once a migration has been applied to a shared environment, it should not be casually edited.

Instead, new changes should be introduced through a new migration.

Example:

Incorrect:
Edit V3 after it has already been executed.

Correct:
Create V7__add_new_feature.sql

This maintains database history and reproducibility.

🧪 Testing Strategy

Testing will be introduced progressively.

The system will eventually include:

Unit Tests

Testing individual services and business logic.

Service
   ↓
Unit Test
Repository Tests

Testing database interaction.

Repository
   ↓
PostgreSQL
Integration Tests

Testing multiple application layers together.

Controller
   ↓
Service
   ↓
Repository
   ↓
Database
API Testing

Using Postman during development.

🛠️ Development Workflow

The development process follows:

1. Define database/domain requirement
          ↓
2. Verify against V1–V6 schema
          ↓
3. Create Entity
          ↓
4. Create Repository
          ↓
5. Create DTO
          ↓
6. Create Mapper
          ↓
7. Create Service Interface
          ↓
8. Create Service Implementation
          ↓
9. Create Controller
          ↓
10. Test
          ↓
11. Commit to Git

This workflow ensures that implementation follows the database and domain design rather than randomly creating disconnected code.

🌿 Git Workflow

The project uses Git for version control.

Current primary branch:

main

Development changes should be committed in logical units.

Example:

git status
git add .
git commit -m "Implement department service layer"
git push origin main

Recommended commit style:

feat: add department service
feat: add document repository
fix: correct department mapping
refactor: improve document service
docs: update README
test: add department service tests

The repository should maintain a clean working tree after completed development milestones.

🚀 Running the Backend

From the backend project directory:

mvn clean spring-boot:run

The application starts on:

http://localhost:8080

The application requires PostgreSQL to be available.

Current database configuration:

Database: archive_db
Host: localhost
Port: 5432
Database Engine: PostgreSQL 18.4
🗃️ Flyway Startup Validation

On successful startup, the application should confirm:

Successfully validated 6 migrations
Current version of schema "public": 6
Schema "public" is up to date

If the database is not at the expected version, Flyway applies pending migrations automatically.

📡 API Development

The backend exposes REST APIs organized around domain resources.

Example endpoint structure:

/api/v1/departments
/api/v1/categories
/api/v1/documents
/api/v1/document-versions
/api/v1/workflows
/api/v1/audit-logs

The API design will follow REST principles and use DTOs rather than exposing database entities directly.

📦 DTO Architecture

The application separates:

Database Entity
       │
       ▼
    Mapper
       │
       ▼
      DTO
       │
       ▼
   REST API

Example:

Department Entity
        ↓
DepartmentMapper
        ↓
DepartmentDto
        ↓
DepartmentController

This prevents the API layer from being tightly coupled to the persistence layer.

🧠 Architectural Principles

The project follows these principles:

Separation of Concerns

Each layer has a clear responsibility.

Controller
    ↓
Service
    ↓
Repository
    ↓
Database
DTO-Based API Design

Database entities should not be directly exposed as API responses.

Single Responsibility

Each class should have one primary responsibility.

Database-First Consistency

The Java domain model must remain consistent with the existing database schema.

Controlled Scope

The project avoids unnecessary complexity.

The current architecture intentionally does not introduce:

❌ Microservices
❌ Redis
❌ RabbitMQ
❌ Unnecessary distributed systems

The objective is to build a complete, secure, maintainable system within the project scope.

🗺️ Development Roadmap
Phase 1 — Foundation
Project setup
Spring Boot configuration
PostgreSQL configuration
Flyway integration
V1–V6 migrations
Basic application startup
Phase 2 — Core Domain
Department module
Category module
User references
DTOs
Mappers
Repositories
Services
Controllers
Phase 3 — Document Management
Document creation
Document metadata
Document upload
Document retrieval
Document versions
SHA-256 checksum handling
Phase 4 — Workflow
Document status management
Submission
Review
Approval
Rejection
Workflow history
Phase 5 — Security
Keycloak integration
JWT validation
Role-based access control
Protected endpoints
Classification-based access rules
Phase 6 — Audit and Search
Audit logging
Document search
Filtering
Sorting
Pagination
Phase 7 — Frontend
React application
Authentication flow
Dashboard
Department management
Document management
Search interface
Workflow interface
Phase 8 — Testing and Quality
Unit tests
Integration tests
API testing
Validation
Error handling
Phase 9 — Deployment
Docker configuration
PostgreSQL container
Keycloak container
Backend container
Frontend container
Deployment documentation
📌 Current Project Status
Completed
Spring Boot backend initialized
PostgreSQL database connected
PostgreSQL 18.4 configured
Flyway configured
Six database migrations validated
Database schema reached version 6
Hibernate/JPA configured
Department module foundation created
Department repository created
Department DTO created
Department mapper created
Department service interface created
Department service implementation created
Department controller created
Application successfully starts on port 8080
Git repository initialized
Main branch established
Current Backend Status
Application: Running
Database: Connected
Flyway: Healthy
Schema: Version 6
JPA: Initialized
Tomcat: Running on port 8080
🔒 Important Development Rule

The system is being developed according to the established V1–V6 database and architecture.

Future development should:

Respect the existing schema.
Avoid breaking previous working modules.
Avoid unnecessary architectural changes.
Introduce database changes through new Flyway migrations.
Maintain consistency between:
Database
Entities
DTOs
Mappers
Repositories
Services
Controllers
Test the application after meaningful changes.
Commit stable milestones to GitHub.

The project should evolve deliberately rather than repeatedly restarting from scratch.


📄 License

This project is currently developed as an academic and professional portfolio project.

License details may be added as the project progresses.
