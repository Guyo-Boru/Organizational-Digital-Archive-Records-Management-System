package com.guyo.archive_system.workflow.mapper;

import com.guyo.archive_system.workflow.dto.DocumentWorkflowDto;
import com.guyo.archive_system.workflow.entity.DocumentWorkflowHistory;

public class DocumentWorkflowMapper {

    private DocumentWorkflowMapper() {
    }

    public static DocumentWorkflowDto toDto(DocumentWorkflowHistory workflow) {

        return DocumentWorkflowDto.builder()
                .workflowId(workflow.getWorkflowId())
                .documentId(workflow.getDocument().getDocumentId())
                .fromStatus(workflow.getFromStatus())
                .toStatus(workflow.getToStatus())
                .comment(workflow.getComment())
                .changedAt(workflow.getChangedAt())
                .changedBy(workflow.getChangedBy().getUserSub())
                .build();

    }

}