package com.digitalarchive.repository;

import com.digitalarchive.domain.entity.DocumentWorkflowHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentWorkflowHistoryRepository extends JpaRepository<DocumentWorkflowHistory, Long> {
    List<DocumentWorkflowHistory> findByDocument_DocumentIdOrderByChangedAtDesc(Long documentId);
}