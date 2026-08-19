package com.guyo.archive_system.document.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.guyo.archive_system.document.dto.DocumentSearchRequest;
import com.guyo.archive_system.document.entity.Document;

import jakarta.persistence.criteria.Predicate;

public final class DocumentSpecification {

    /**
     * Escape character used with the 3-arg {@code criteriaBuilder.like}
     * overload so escaped {@code %}/{@code _} in a search term are matched
     * literally instead of as SQL wildcards.
     */
    private static final char LIKE_ESCAPE_CHAR = '\\';

    private DocumentSpecification() {
    }

    /**
     * Escapes the SQL {@code LIKE} wildcard characters ({@code %}, {@code _})
     * and the escape character itself in raw user input, so it is safe to
     * wrap in {@code %...%} and pass to a 3-arg {@code like(...)} call with
     * {@link #LIKE_ESCAPE_CHAR}.
     */
    private static String escapeLike(String value) {

        return value
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");

    }

    public static Specification<Document> search(
            DocumentSearchRequest request) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            final DocumentSearchRequest criteria =
                    request != null
                            ? request
                            : DocumentSearchRequest.builder().build();

            /*
             * Soft-delete filter.
             *
             * Excluded by default so deleted documents never leak into
             * normal search/browse results; callers can opt in explicitly.
             */
            if (!criteria.isIncludeDeleted()) {

                predicates.add(
                        criteriaBuilder.isNull(
                                root.get("deletedAt")
                        )
                );
            }

            /*
             * General text search.
             *
             * Searches:
             * - reference number
             * - title
             * - description
             *
             * User input is escaped before being wrapped in wildcards so a
             * literal '%' or '_' in the search term is matched literally
             * instead of being interpreted as a SQL wildcard (which would
             * both return misleading results and let a crafted term like
             * "%%%%%%%%%%" force an expensive full scan).
             */
            if (criteria.getQ() != null
                    && !criteria.getQ().isBlank()) {

                String searchTerm =
                        "%" + escapeLike(criteria.getQ().trim().toLowerCase()) + "%";

                Predicate referenceNumber =
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("referenceNumber")
                                ),
                                searchTerm,
                                LIKE_ESCAPE_CHAR
                        );

                Predicate title =
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("title")
                                ),
                                searchTerm,
                                LIKE_ESCAPE_CHAR
                        );

                Predicate description =
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("description")
                                ),
                                searchTerm,
                                LIKE_ESCAPE_CHAR
                        );

                predicates.add(
                        criteriaBuilder.or(
                                referenceNumber,
                                title,
                                description
                        )
                );
            }

            /*
             * Exact reference number filter.
             */
            if (criteria.getReferenceNumber() != null
                    && !criteria.getReferenceNumber().isBlank()) {

                predicates.add(
                        criteriaBuilder.equal(
                                criteriaBuilder.lower(
                                        root.get("referenceNumber")
                                ),
                                criteria.getReferenceNumber()
                                        .trim()
                                        .toLowerCase()
                        )
                );
            }

            /*
             * Title filter.
             */
            if (criteria.getTitle() != null
                    && !criteria.getTitle().isBlank()) {

                String title =
                        "%" + escapeLike(criteria.getTitle().trim().toLowerCase()) + "%";

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("title")
                                ),
                                title,
                                LIKE_ESCAPE_CHAR
                        )
                );
            }

            /*
             * Category filter (any of).
             */
            if (criteria.getCategoryIds() != null
                    && !criteria.getCategoryIds().isEmpty()) {

                predicates.add(
                        root.get("category")
                                .get("categoryId")
                                .in(criteria.getCategoryIds())
                );
            }

            /*
             * Department filter (any of).
             */
            if (criteria.getDepartmentIds() != null
                    && !criteria.getDepartmentIds().isEmpty()) {

                predicates.add(
                        root.get("department")
                                .get("departmentId")
                                .in(criteria.getDepartmentIds())
                );
            }

            /*
             * Classification filter (any of).
             */
            if (criteria.getClassifications() != null
                    && !criteria.getClassifications().isEmpty()) {

                predicates.add(
                        root.get("classification")
                                .in(criteria.getClassifications())
                );
            }

            /*
             * Document status filter (any of).
             */
            if (criteria.getStatuses() != null
                    && !criteria.getStatuses().isEmpty()) {

                predicates.add(
                        root.get("status")
                                .in(criteria.getStatuses())
                );
            }

            /*
             * Created-by filter.
             */
            if (criteria.getCreatedBy() != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("createdBy")
                                        .get("userSub"),
                                criteria.getCreatedBy()
                        )
                );
            }

            /*
             * Created-at date range.
             */
            if (criteria.getCreatedFrom() != null) {

                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("createdAt"),
                                criteria.getCreatedFrom()
                        )
                );
            }

            if (criteria.getCreatedTo() != null) {

                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("createdAt"),
                                criteria.getCreatedTo()
                        )
                );
            }

            /*
             * Updated-at date range.
             */
            if (criteria.getUpdatedFrom() != null) {

                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("updatedAt"),
                                criteria.getUpdatedFrom()
                        )
                );
            }

            if (criteria.getUpdatedTo() != null) {

                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("updatedAt"),
                                criteria.getUpdatedTo()
                        )
                );
            }

            /*
             * Archived-at date range.
             */
            if (criteria.getArchivedFrom() != null) {

                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("archivedAt"),
                                criteria.getArchivedFrom()
                        )
                );
            }

            if (criteria.getArchivedTo() != null) {

                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("archivedAt"),
                                criteria.getArchivedTo()
                        )
                );
            }

            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0])
            );
        };
    }
}
