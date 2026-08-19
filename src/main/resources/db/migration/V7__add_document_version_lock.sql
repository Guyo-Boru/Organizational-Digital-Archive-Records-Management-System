-- Adds an optimistic-locking column so concurrent updates to the same
-- document (e.g. two reviewers changing workflow status at once) fail
-- fast instead of silently overwriting one another.
ALTER TABLE documents
    ADD COLUMN version BIGINT NOT NULL DEFAULT 0;
