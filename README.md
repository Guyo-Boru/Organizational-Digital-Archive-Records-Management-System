Organizational Digital Archive & Records Management System

A secure web-based system for the centralized management, classification, storage, versioning, approval, retrieval, and auditing of official organizational documents and records.

The system is designed to replace fragmented and inefficient document-handling processes with a structured digital archive that provides controlled access, document integrity, traceability, and efficient information retrieval.

Table of Contents
1. Project Overview
2. Problem Statement
3. Project Objectives
4. Project Scope
5. Core Features
6. System Architecture
7. Technology Stack
8. Domain Model
9. Database Design
10. Database Migration History
11. Core Domain Entities
12. Document Lifecycle
13. Document Versioning and Integrity
14. Workflow and Approval
15. Audit Logging
16. Security Architecture
17. Backend Architecture
18. Project Structure
19. API Design
20. Current Implementation Status
21. Running the Application
22. Database Configuration
23. Flyway Database Migrations
24. Development Workflow
25. Git and GitHub Workflow
26. Development Roadmap
27. Project Design Principles
28. Future Deployment
29. License
1. Project Overview

The Organizational Digital Archive & Records Management System is a full-stack information system for managing official organizational documents throughout their lifecycle.

The system provides a centralized platform where authorized users can:

Register official documents
Classify documents
Organize documents by department and category
Store document files and metadata
Maintain document versions
Submit documents for review and approval
Track document workflow history
Add notes to documents
Search and retrieve records
Monitor system activities through audit logs
Protect document integrity using SHA-256 checksums
Access system functionality according to assigned roles

The system is being developed as a modular monolithic application with a Spring Boot backend, React frontend, PostgreSQL database, Flyway migration management, and Keycloak-based identity and role management.

2. Problem Statement

Organizations commonly manage official records using a combination of:

Paper-based documents
Unstructured digital folders
Email attachments
Local computer storage
Shared drives without sufficient access control

These approaches create several problems:

Difficulty locating documents
Duplicate files
Loss of document history
Unauthorized access
Lack of centralized storage
Weak accountability
No reliable approval history
Difficulty determining which version is current
Limited auditability

This project addresses these problems by providing a centralized and structured digital archive system.

3. Project Objectives
General Objective

To design and develop a secure digital archive and records management system for the centralized storage, organization, retrieval, versioning, workflow management, and auditing of organizational documents.

Specific Objectives

The system aims to:

Centralize organizational document storage.
Organize documents by departments and categories.
Support document classification based on sensitivity.
Maintain document version history.
Provide document approval and workflow tracking.
Enable efficient document search and retrieval.
Maintain an audit trail of important system actions.
Protect document integrity through SHA-256 checksums.
Provide controlled access through authentication and role-based authorization.
Reduce reliance on fragmented and manual record-management processes.
4. Project Scope
Included in Scope

The system includes:

Department management
Category management
User identity mapping
Document registration
Document metadata management
Document classification
Document status management
Document versioning
File upload and storage
SHA-256 checksum generation
Document notes
Document approval workflow
Workflow history
Search and filtering
Audit logging
Dashboard statistics
Authentication
Role-based access control
REST API
React frontend
PostgreSQL database
Flyway database migrations
Docker-based deployment preparation
Deliberately Excluded

To maintain a realistic development scope, the project does not use:

Microservices
Redis
RabbitMQ
Complex event-driven infrastructure
Unnecessary distributed systems
Multiple independent backend services

The project follows a modular monolithic architecture because it provides sufficient scalability and maintainability for the system's scope without introducing unnecessary operational complexity.

5. Core Features
5.1 Department Management

Departments represent organizational units responsible for documents.

The system supports:

Viewing all departments
Retrieving a department by UUID
Associating users with departments
Associating documents with organizational ownership where applicable
5.2 Category Management

Categories provide an organizational classification for documents.

Examples may include:

Administrative
Financial
Human Resources
Legal
Technical
Reports

The actual categories are managed through the database seed data and can be expanded according to organizational requirements.

5.3 Document Management

The document module manages the primary record of an official document.

Document metadata may include:

Document title
Description
Department
Category
Classification level
Current status
Owner or creator
Creation date
Last modification date
Current version

The document record is separate from the physical file version.

This distinction allows one document to have multiple versions over time.

5.4 Document Version Management

Each uploaded file version is recorded separately.

A document may have:

Document
    │
    ├── Version 1
    ├── Version 2
    └── Version 3

The system maintains version history instead of replacing the previous file without traceability.

5.5 Document Classification

Documents can be assigned classification levels according to their sensitivity.

The classification system is represented by the database enum:

classification_level

This supports controlled handling of sensitive organizational records.

5.6 Document Notes

Authorized users can add notes associated with documents.

Notes may be used for:

Review comments
Internal remarks
Clarifications
Approval-related observations

The type of note is controlled using:

note_type
5.7 Document Workflow

Documents may pass through different workflow states.

A typical workflow may be:

Draft
  ↓
Submitted
  ↓
Under Review
  ↓
Approved

or:

Submitted
    ↓
Rejected
    ↓
Returned for Revision
    ↓
Resubmitted

Each important workflow transition is recorded in the workflow history.

5.8 Search and Retrieval

The system is designed to allow users to locate documents using criteria such as:

Title
Category
Department
Classification level
Status
Date
Document owner

Search and filtering are important because the primary purpose of a digital archive is not merely to store files, but to make them retrievable.

5.9 Audit Logging

Important system activities are recorded in the audit log.

Examples include:

Document creation
Document update
Document deletion
Document viewing
File upload
Version creation
Workflow transition
Approval
Rejection

The audit system uses:

audit_action
resource_type

to describe activities and affected resources.

6. System Architecture

The system uses a modular monolithic architecture.

┌──────────────────────────────┐
│        React Frontend        │
│                              │
│  Dashboard                   │
│  Documents                   │
│  Search                      │
│  Administration              │
└──────────────┬───────────────┘
               │
               │ REST API
               ▼
┌──────────────────────────────┐
│     Spring Boot Backend      │
│                              │
│  Department Module           │
│  Category Module             │
│  Document Module             │
│  Version Module              │
│  Workflow Module             │
│  Audit Module                │
│                              │
│  Business Logic              │
│  DTOs                        │
│  Mappers                     │
│  Repositories                │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│       PostgreSQL 18          │
│                              │
│  Database Schema             │
│  V1 – V6 Migrations          │
│  Indexes                     │
│  Views                       │
└──────────────────────────────┘

Authentication and role management are handled through Keycloak.

7. Technology Stack
Backend
Technology	Version / Status
Java	25.0.3
Spring Boot	4.1.0
Spring Web MVC	Spring Boot managed
Spring Data JPA	Spring Boot managed
Hibernate ORM	7.4.1.Final
Maven	3.9.x
Lombok	Configured
MapStruct	Configured
Database
Technology	Version / Status
PostgreSQL	18.4
PostgreSQL JDBC Driver	Configured
Flyway	Configured
pgcrypto	Used by database schema
Frontend
Technology	Status
React	Planned / frontend development
JavaScript	Planned / frontend development
Vite	Frontend development
Security
Technology	Purpose
Keycloak	Authentication and identity
OAuth 2.0	Authorization framework
OpenID Connect	Identity protocol
RBAC	Role-based access control
Development Tools
Visual Studio Code
Git
GitHub
Postman
PostgreSQL tools
8. Domain Model

The core domain is built around organizational records and their lifecycle.

Department
     │
     └── Users

Category
     │
     └── Documents

User
     │
     ├── Documents
     ├── Document Versions
     ├── Notes
     ├── Workflow History
     └── Audit Logs

Document
     │
     ├── Document Versions
     ├── Document Notes
     └── Workflow History

The database is the authoritative source for the relationships.

The backend entities must remain consistent with the established V1–V6 schema.

9. Database Design

The database is PostgreSQL-based and managed using Flyway.

The core database tables are:

departments
categories
users
documents
document_versions
document_notes
document_workflow_history
audit_logs

The database also contains:

Foreign-key relationships
Unique constraints
Check constraints
PostgreSQL enum types
Indexes
Database views
UUID identifiers
SHA-256 checksum storage
Database Enums

The system uses controlled database values through PostgreSQL enum types.

document_status
classification_level
note_type
audit_action
resource_type

Using controlled enum values prevents inconsistent data such as:

approved
Approved
APPROVED
approve

from being stored as separate values.

10. Database Migration History

Flyway manages the database schema using versioned migrations.

V1 ──► V2 ──► V3 ──► V4 ──► V5 ──► V6
V1 — Initial Schema

Creates the core database foundation, including:

PostgreSQL extensions
Enum types
Departments
Categories
Users
Documents
Document versions
Notes
Workflow history
Audit logs
Foreign-key relationships
V2 — Seed Data

Provides initial reference data, including:

Departments
Categories

This allows the application to operate with initial master data.

V3 — Indexes

Creates indexes to improve query performance.

Indexes are especially important for:

Document searching
Foreign-key lookups
Status filtering
Date filtering
Audit history retrieval
V4 — Database Views

Creates database views for commonly required read operations.

The project includes views such as:

vw_document_details
vw_document_statistics
vw_recent_documents
vw_audit_history

Views simplify frequently used queries and provide structured read models.

V5 — Schema Enhancement

Contains subsequent database improvements that extend or refine the initial database foundation.

V6 — Current Schema Version

Contains the latest database changes currently applied to the project.

Current database status:

Database: archive_db
PostgreSQL: 18.4
Current schema version: 6
Validated migrations: 6
Status: Up to date
11. Core Domain Entities
Department

Represents an organizational department.

Responsibilities:

Identify organizational units
Group users
Support organizational document management
Category

Represents a classification category for documents.

Responsibilities:

Organize documents
Support filtering
Improve document retrieval
User

Represents an application user identity.

The system stores the user's Keycloak identity reference rather than implementing a separate authentication system inside the application database.

This keeps identity management centralized.

Document

Represents the logical official record.

A document is not simply a file.

It contains the business identity and metadata of the record.

A document may have multiple versions.

Document Version

Represents a specific uploaded version of a document.

Each version can contain:

File information
Version number
File size
MIME type
Storage information
SHA-256 checksum
Uploading user
Timestamp
Document Note

Represents comments or notes associated with a document.

Document Workflow History

Records the progression of a document through workflow states.

Example:

DRAFT
  ↓
SUBMITTED
  ↓
UNDER_REVIEW
  ↓
APPROVED

Each transition can record:

Previous state
New state
User responsible
Comment or reason
Timestamp
Audit Log

Records important activities performed within the system.

An audit record should answer:

Who?
Did what?
To which resource?
When?
12. Document Lifecycle

The document lifecycle follows this general process:

Create Document
       │
       ▼
Add Metadata
       │
       ▼
Upload File
       │
       ▼
Create Version
       │
       ▼
Calculate SHA-256
       │
       ▼
Submit for Review
       │
       ▼
Review
       │
 ┌─────┴─────┐
 ▼           ▼
Approve     Reject
 │           │
 ▼           ▼
Archive     Revise

The exact workflow is controlled by the document status and workflow history.

13. Document Versioning and Integrity

The system does not overwrite previous document versions.

Instead:

Document
    │
    ├── Version 1
    ├── Version 2
    └── Version 3

The current version is identified through the document's current version reference.

SHA-256 Integrity Verification

A SHA-256 checksum is generated for each file version.

Conceptually:

File
  ↓
SHA-256 Algorithm
  ↓
64-character hexadecimal checksum

Example:

a3f5...64-character-hash

If the file changes, its checksum changes.

This allows the system to detect whether a file has been modified.

The checksum is stored in:

checksum_sha256
14. Workflow and Approval

The workflow system tracks important state transitions.

Example:

Draft
  ↓
Submitted
  ↓
Under Review
  ↓
Approved

Rejected documents may follow:

Under Review
      ↓
Rejected
      ↓
Returned for Revision
      ↓
Resubmitted

Workflow history preserves the sequence of decisions.

This is important because the system should not only show the current state of a document.

It should also explain how the document reached that state.

15. Audit Logging

Audit logs provide accountability.

The system should be able to answer:

Who uploaded a document?
Who changed its metadata?
Who approved it?
Who rejected it?
Who created a new version?
Who accessed or modified a resource?

The audit system records:

Actor
Action
Resource
Resource ID
Timestamp
Details
16. Security Architecture

Keycloak is responsible for:

User authentication
Identity management
Role management
Token issuance

The Spring Boot application is responsible for:

Validating authenticated requests
Applying business rules
Enforcing application-level access requirements
Processing authorized operations

Architecture:

User
 │
 ▼
Keycloak
 │
 ├── Authentication
 ├── Identity
 └── Roles
       │
       ▼
Spring Boot API
       │
       ├── Business Rules
       ├── Document Operations
       └── Data Access

The application database stores the relationship between application records and Keycloak users using the user's Keycloak identity reference.

17. Backend Architecture

Each feature follows a layered modular structure:

Controller
    ↓
Service Interface
    ↓
Service Implementation
    ↓
Repository
    ↓
Entity
    ↓
Database

DTOs and mappers separate API models from database entities.

Example:

HTTP Request
     ↓
Controller
     ↓
DTO
     ↓
Service
     ↓
Mapper
     ↓
Entity
     ↓
Repository
     ↓
PostgreSQL
18. Project Structure

Current backend structure:

archive-system/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/guyo/archive_system/
│   │   │       │
│   │   │       ├── department/
│   │   │       │   ├── controller/
│   │   │       │   │   └── DepartmentController.java
│   │   │       │   │
│   │   │       │   ├── dto/
│   │   │       │   │   └── DepartmentDto.java
│   │   │       │   │
│   │   │       │   ├── entity/
│   │   │       │   │   └── Department.java
│   │   │       │   │
│   │   │       │   ├── mapper/
│   │   │       │   │   └── DepartmentMapper.java
│   │   │       │   │
│   │   │       │   ├── repository/
│   │   │       │   │   └── DepartmentRepository.java
│   │   │       │   │
│   │   │       │   └── service/
│   │   │       │       ├── DepartmentService.java
│   │   │       │       └── DepartmentServiceImpl.java
│   │   │       │
│   │   │       └── ArchiveSystemApplication.java
│   │   │
│   │   └── resources/
│   │       ├── db/
│   │       │   └── migration/
│   │       │       ├── V1__init_schema.sql
│   │       │       ├── V2__seed_data.sql
│   │       │       ├── V3__create_indexes.sql
│   │       │       ├── V4__create_views.sql
│   │       │       ├── V5__...
│   │       │       └── V6__...
│   │       │
│   │       └── application.yml
│   │
│   └── test/
│
├── pom.xml
├── README.md
└── .gitignore

As development continues, additional modules will follow the same structure:

category/
document/
documentversion/
workflow/
audit/
19. API Design

The backend exposes RESTful APIs.

Current implemented endpoints include:

GET /api/departments

Returns all departments.

GET /api/departments/{departmentId}

Returns a department by UUID.

Future modules will follow the same REST conventions.

Example planned API structure:

/api/departments
/api/categories
/api/documents
/api/documents/{id}/versions
/api/documents/{id}/notes
/api/documents/{id}/workflow
/api/audit-logs

Endpoints are only marked as implemented after the functionality has actually been developed and tested.

20. Current Implementation Status
Completed
[✓] Project initialized
[✓] Spring Boot backend configured
[✓] Maven build configured
[✓] PostgreSQL connected
[✓] Flyway configured
[✓] V1 migration completed
[✓] V2 migration completed
[✓] V3 migration completed
[✓] V4 migration completed
[✓] V5 migration completed
[✓] V6 migration completed
[✓] Database schema validated
[✓] PostgreSQL 18.4 connected
[✓] Department entity implemented
[✓] Department DTO implemented
[✓] Department repository implemented
[✓] Department mapper implemented
[✓] Department service interface implemented
[✓] Department service implementation implemented
[✓] Department controller implemented
[✓] Department API implemented
[✓] Application successfully starts
[✓] Git repository initialized
[✓] GitHub repository connected
In Progress
[ ] Department API testing
[ ] Category module
[ ] Continued backend module development
Planned
[ ] Keycloak integration
[ ] User identity integration
[ ] Document module
[ ] Document version module
[ ] File storage
[ ] SHA-256 file integrity
[ ] Document notes
[ ] Workflow module
[ ] Audit module
[ ] Search and filtering
[ ] Dashboard statistics
[ ] React frontend
[ ] Docker configuration
[ ] Deployment
21. Running the Application
Prerequisites

Install:

Java 25
Maven
PostgreSQL 18
Git
Database

Create the database:

archive_db

Configure the database connection in:

src/main/resources/application.yml
Run the Application

From the project root:

mvn spring-boot:run

For a clean build:

mvn clean spring-boot:run

The backend runs at:

http://localhost:8080
22. Database Configuration

The application connects to:

jdbc:postgresql://localhost:5432/archive_db

The application uses:

Database: PostgreSQL
Database: archive_db
Schema: public

The database connection is managed by Spring Boot and HikariCP.

23. Flyway Database Migrations

Flyway automatically manages schema changes.

On application startup:

Application
    ↓
Connect to Database
    ↓
Flyway Validation
    ↓
Check Migration History
    ↓
Apply New Migrations
    ↓
Start Application

Example startup confirmation:

Successfully validated 6 migrations
Current version of schema "public": 6
Schema "public" is up to date

Migration files are stored in:

src/main/resources/db/migration/

Once a migration has been applied to a shared database, it should not be casually edited.

New schema changes should normally be added as a new migration.

24. Development Workflow

The project follows a domain-driven implementation process.

Understand Database Design
          ↓
Define Domain Requirement
          ↓
Create Entity
          ↓
Create DTO
          ↓
Create Repository
          ↓
Create Mapper
          ↓
Create Service Interface
          ↓
Create Service Implementation
          ↓
Create Controller
          ↓
Run Application
          ↓
Test API
          ↓
Commit Changes
          ↓
Push to GitHub
25. Git and GitHub Workflow

Git is used for version control.

Before working:

git status

After implementing a feature:

git add .

Create a meaningful commit:

git commit -m "Implement department module"

Push to GitHub:

git push origin main

Recommended development cycle:

Implement
   ↓
Run
   ↓
Test
   ↓
Review
   ↓
Commit
   ↓
Push

The project should be pushed regularly instead of waiting until the end of development.

26. Development Roadmap
Phase 1 — Foundation
Project setup
Spring Boot configuration
PostgreSQL connection
Flyway integration
V1–V6 database schema
GitHub repository
Phase 2 — Master Data
Department module
Category module
Phase 3 — Identity and Security
Keycloak setup
User identity integration
Authentication
Role-based authorization
Phase 4 — Document Management
Document entity
Document metadata
Classification
Status management
Document APIs
Phase 5 — File and Version Management
File upload
File storage
Document version creation
SHA-256 checksum generation
Current-version tracking
Phase 6 — Workflow
Document submission
Review
Approval
Rejection
Workflow history
Phase 7 — Notes and Audit
Document notes
Audit events
Audit history
User activity tracking
Phase 8 — Search and Dashboard
Document search
Filtering
Statistics
Recent documents
Dashboard views
Phase 9 — React Frontend
Authentication interface
Dashboard
Department management
Category management
Document management
Search
Workflow screens
Phase 10 — Deployment
Docker configuration
Environment configuration
Database deployment
Backend deployment
Frontend deployment
Deployment documentation
27. Project Design Principles
Database Consistency

The backend must remain consistent with the established V1–V6 database design.

The application should not introduce entities or relationships that contradict the database schema without deliberately updating the database through a new migration.

Modular Design

Each major business domain is organized into its own module.

department/
category/
document/
workflow/
audit/
Separation of Responsibilities

Each layer has a clear responsibility:

Layer	Responsibility
Controller	HTTP/API communication
DTO	API data transfer
Mapper	DTO/entity conversion
Service	Business logic
Repository	Database access
Entity	Domain/database representation
Scope Control

The project intentionally avoids unnecessary technologies.

The goal is not to use the largest possible technology stack.

The goal is to build a complete, secure, maintainable archive system within the available development time.

Git Discipline

Every meaningful feature should be:

Implemented
    ↓
Tested
    ↓
Committed
    ↓
Pushed
28. Future Deployment

The system is intended to support containerized deployment using Docker.

The planned deployment architecture is:

┌──────────────┐
│   Frontend   │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│   Backend    │
└──────┬───────┘
       │
       ├──────────────┐
       ▼              ▼
┌──────────────┐ ┌──────────────┐
│ PostgreSQL   │ │  Keycloak    │
└──────────────┘ └──────────────┘

The deployment configuration will be added after the core application functionality has been completed.

29. License

This project is developed for educational and internship purposes.
