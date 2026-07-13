Drop your existing V1__init_schema.sql through V6__*.sql files (the ones from
slide 13 of your deck) directly into this folder: src/main/resources/db/migration/

Flyway auto-discovers any file matching V<version>__<description>.sql here and
runs them in order on application startup (see spring.flyway config in application.yml).

IMPORTANT: the JPA entities in domain/entity/ use Hibernate's NAMED_ENUM mapping,
which means the Postgres enum type names created by your migrations must match
exactly what the entities expect:
  - document_status        (DRAFT, SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED, ARCHIVED)
  - classification_level   (PUBLIC, INTERNAL, CONFIDENTIAL, SECRET)
  - note_type               (GENERAL, REVIEW, REJECTION_REASON, SYSTEM)
  - audit_action            (CREATE, UPDATE, DELETE, VIEW, UPLOAD, DOWNLOAD, LOGIN, LOGOUT, APPROVE, REJECT, ARCHIVE, RESTORE)
  - resource_type           (DOCUMENT, DOCUMENT_VERSION, USER, DEPARTMENT, CATEGORY)

If your actual enum values differ (e.g. you didn't include VIEW/LOGIN/LOGOUT/ARCHIVE/RESTORE
in audit_action, or your resource_type list is different), either:
  (a) adjust the Java enums in domain/enums/ to match your SQL exactly, or
  (b) adjust your SQL to match the Java enums.
They must be identical or Hibernate will fail to start with a schema validation error.
