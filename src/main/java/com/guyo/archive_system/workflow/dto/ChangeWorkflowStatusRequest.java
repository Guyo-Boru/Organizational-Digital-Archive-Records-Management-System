package com.guyo.archive_system.workflow.dto;

import com.guyo.archive_system.document.enums.DocumentStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeWorkflowStatusRequest {

    @NotNull
    private DocumentStatus toStatus;

    private String comment;

}