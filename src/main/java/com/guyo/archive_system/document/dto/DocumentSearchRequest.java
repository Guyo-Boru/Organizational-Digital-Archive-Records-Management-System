package com.guyo.archive_system.document.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.guyo.archive_system.document.enums.ClassificationLevel;
import com.guyo.archive_system.document.enums.DocumentStatus;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Filter/search criteria for documents.
 * <p>
 * Every field is optional; only supplied criteria are applied (AND'ed
 * together), and fields that accept a collection are matched with an OR
 * within that field (e.g. status IN (...)). Callers can therefore search
 * broadly with just {@code q}, or narrow precisely by combining several
 * filters at once.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentSearchRequest {

    /** Free-text keyword, matched against reference number, title and description. */
    @Size(max = 200, message = "q must be at most 200 characters")
    private String q;

    @Size(max = 100, message = "referenceNumber must be at most 100 characters")
    private String referenceNumber;

    @Size(max = 200, message = "title must be at most 200 characters")
    private String title;

    private List<UUID> categoryIds;

    private List<UUID> departmentIds;

    private List<ClassificationLevel> classifications;

    private List<DocumentStatus> statuses;

    private UUID createdBy;

    private OffsetDateTime createdFrom;

    private OffsetDateTime createdTo;

    private OffsetDateTime updatedFrom;

    private OffsetDateTime updatedTo;

    private OffsetDateTime archivedFrom;

    private OffsetDateTime archivedTo;

    /** When true, soft-deleted documents are included in the results. Defaults to false. */
    @Builder.Default
    private boolean includeDeleted = false;

}
