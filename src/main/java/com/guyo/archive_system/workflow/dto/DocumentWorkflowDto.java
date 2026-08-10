package com.guyo.archive_system.workflow.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.guyo.archive_system.document.enums.DocumentStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DocumentWorkflowDto {

    private UUID workflowId;

    private UUID documentId;

    private DocumentStatus fromStatus;

    private DocumentStatus toStatus;

    private String comment;

    private OffsetDateTime changedAt;

    private UUID changedBy;

}