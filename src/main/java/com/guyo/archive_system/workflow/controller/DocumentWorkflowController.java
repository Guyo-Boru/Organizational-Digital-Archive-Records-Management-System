package com.guyo.archive_system.workflow.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.guyo.archive_system.workflow.dto.ChangeWorkflowStatusRequest;
import com.guyo.archive_system.workflow.dto.DocumentWorkflowDto;
import com.guyo.archive_system.workflow.service.DocumentWorkflowService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/documents/{documentId}/workflow")
public class DocumentWorkflowController {

    private final DocumentWorkflowService workflowService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentWorkflowDto changeStatus(

            @PathVariable UUID documentId,

            @Validated
            @RequestBody ChangeWorkflowStatusRequest request,

            @AuthenticationPrincipal Jwt jwt

    ) {

        return workflowService.changeStatus(

                documentId,

                UUID.fromString(jwt.getSubject()),

                request

        );

    }

    @GetMapping
    public List<DocumentWorkflowDto> history(

            @PathVariable UUID documentId

    ) {

        return workflowService.getHistory(documentId);

    }

}