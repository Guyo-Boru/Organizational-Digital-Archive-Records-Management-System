package com.guyo.archive_system.document.version.dto;

import org.springframework.core.io.Resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentVersionDownload {

    private String fileName;

    private String mimeType;

    private Long fileSize;

    private Resource resource;
}