package com.guyo.archive_system.document.repository;

import java.util.Set;

import org.springframework.data.domain.Sort;

/**
 * Whitelists which Document properties may be used for sorting on public
 * list/search endpoints.
 * <p>
 * Without this, an unknown or relation-traversing sort property supplied by
 * a client would surface as an unhandled Hibernate exception (HTTP 500)
 * instead of a clean validation error.
 */
public final class DocumentSortProperties {

    public static final Set<String> ALLOWED = Set.of(
            "referenceNumber",
            "title",
            "classification",
            "status",
            "createdAt",
            "updatedAt",
            "archivedAt"
    );

    private DocumentSortProperties() {
    }

    public static void validate(Sort sort) {

        if (sort == null || sort.isUnsorted()) {
            return;
        }

        for (Sort.Order order : sort) {

            if (!ALLOWED.contains(order.getProperty())) {

                throw new IllegalArgumentException(
                        "Cannot sort documents by '" + order.getProperty()
                                + "'. Allowed sort properties: " + ALLOWED
                );
            }
        }
    }
}
