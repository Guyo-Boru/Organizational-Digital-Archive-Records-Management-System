package com.guyo.archive_system.document.service;

import com.guyo.archive_system.document.dto.DocumentDto;

import java.util.List;
import java.util.UUID;

public interface DocumentService {

    List<DocumentDto> getAll();

    DocumentDto getById(UUID documentId);

}