package com.guyo.archive_system.document.repository;

import com.guyo.archive_system.category.entity.Category;
import com.guyo.archive_system.department.entity.Department;
import com.guyo.archive_system.document.entity.Document;
import com.guyo.archive_system.document.enums.DocumentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {

    Optional<Document> findByReferenceNumber(String referenceNumber);

    boolean existsByReferenceNumber(String referenceNumber);

    List<Document> findByStatus(DocumentStatus status);

    List<Document> findByCategory(Category category);

    List<Document> findByDepartment(Department department);

}