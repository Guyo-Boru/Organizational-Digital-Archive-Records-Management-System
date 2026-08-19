package com.guyo.archive_system.document.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.guyo.archive_system.category.entity.Category;
import com.guyo.archive_system.department.entity.Department;
import com.guyo.archive_system.document.enums.ClassificationLevel;
import com.guyo.archive_system.document.enums.DocumentStatus;
import com.guyo.archive_system.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "document_id")
    private UUID documentId;

    @Column(
        name = "reference_number",
        nullable = false,
        unique = true,
        length = 50
    )
    private String referenceNumber;

    @Column(
        name = "title",
        nullable = false,
        length = 300
    )
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "category_id",
        nullable = false
    )
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "department_id",
        nullable = false
    )
    private Department department;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(
        name = "classification",
        nullable = false,
        columnDefinition = "classification_level"
    )
    private ClassificationLevel classification;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(
        name = "status",
        nullable = false,
        columnDefinition = "document_status"
    )
    private DocumentStatus status;

    @Column(name = "current_version_id")
    private UUID currentVersionId;

    @Column(name = "archived_at")
    private OffsetDateTime archivedAt;

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "created_by",
        nullable = false
    )
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Column(name = "deleted_by")
    private UUID deletedBy;

    /**
     * Optimistic-locking token. Without this, two concurrent updates (e.g.
     * two reviewers changing workflow status at the same time) silently
     * overwrite one another; with it, the second commit fails fast with an
     * {@code ObjectOptimisticLockingFailureException} instead of quietly
     * losing data.
     */
    @Version
    @Column(name = "version", nullable = false)
    private long version;
}