package com.guyo.archive_system.document.dto;

import java.util.UUID;

import com.guyo.archive_system.document.enums.ClassificationLevel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Payload for updating a document's descriptive metadata.
 * <p>
 * This intentionally covers only title/description/category/department/
 * classification — the fields an uploader or archivist would correct.
 * {@code status} is not editable here; status changes are exclusively the
 * workflow module's responsibility (submit/review/approve/reject), so
 * every transition stays recorded in document_workflow_history. A generic
 * PUT that could silently flip status would bypass that entirely.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDocumentRequest {

    @NotBlank(message = "title is required")
    @Size(max = 300, message = "title must be at most 300 characters")
    private String title;

    private String description;

    @NotNull(message = "categoryId is required")
    private UUID categoryId;

    @NotNull(message = "departmentId is required")
    private UUID departmentId;

    @NotNull(message = "classification is required")
    private ClassificationLevel classification;
}
