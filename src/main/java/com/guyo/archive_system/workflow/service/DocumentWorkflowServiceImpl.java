package com.guyo.archive_system.workflow.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guyo.archive_system.document.entity.Document;
import com.guyo.archive_system.document.repository.DocumentRepository;
import com.guyo.archive_system.user.entity.User;
import com.guyo.archive_system.user.repository.UserRepository;
import com.guyo.archive_system.workflow.dto.ChangeWorkflowStatusRequest;
import com.guyo.archive_system.workflow.dto.DocumentWorkflowDto;
import com.guyo.archive_system.workflow.entity.DocumentWorkflowHistory;
import com.guyo.archive_system.workflow.mapper.DocumentWorkflowMapper;
import com.guyo.archive_system.workflow.repository.DocumentWorkflowRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentWorkflowServiceImpl
        implements DocumentWorkflowService {

    private final DocumentRepository documentRepository;

    private final UserRepository userRepository;

    private final DocumentWorkflowRepository workflowRepository;

    @Override
    public DocumentWorkflowDto changeStatus(

            UUID documentId,

            UUID userId,

            ChangeWorkflowStatusRequest request

    ) {

        Document document = documentRepository.findById(documentId)

                .orElseThrow(() ->
                        new RuntimeException("Document not found."));

        User user = userRepository.findById(userId)

                .orElseThrow(() ->
                        new RuntimeException("User not found."));

        DocumentWorkflowHistory history =

                DocumentWorkflowHistory.builder()

                        .document(document)

                        .fromStatus(document.getStatus())

                        .toStatus(request.getToStatus())

                        .comment(request.getComment())

                        .changedBy(user)

                        .build();

        workflowRepository.save(history);

        document.setStatus(request.getToStatus());

        documentRepository.save(document);

        return DocumentWorkflowMapper.toDto(history);

    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentWorkflowDto> getHistory(UUID documentId) {

        return workflowRepository

                .findByDocumentDocumentIdOrderByChangedAtDesc(documentId)

                .stream()

                .map(DocumentWorkflowMapper::toDto)

                .toList();

    }

}