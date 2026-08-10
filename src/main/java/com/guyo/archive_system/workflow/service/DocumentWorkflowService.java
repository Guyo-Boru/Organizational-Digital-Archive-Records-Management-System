package com.guyo.archive_system.workflow.service;

import java.util.List;
import java.util.UUID;

import com.guyo.archive_system.workflow.dto.ChangeWorkflowStatusRequest;
import com.guyo.archive_system.workflow.dto.DocumentWorkflowDto;

public interface DocumentWorkflowService {

    DocumentWorkflowDto changeStatus(

            UUID documentId,

            UUID userId,

            ChangeWorkflowStatusRequest request

    );

    List<DocumentWorkflowDto> getHistory(UUID documentId);

}