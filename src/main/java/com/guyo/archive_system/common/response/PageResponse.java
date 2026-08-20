package com.guyo.archive_system.common.response;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Getter;

/**
 * Standard pagination envelope returned by list/search endpoints.
 * <p>
 * Keeps the API contract independent of Spring Data's Page type so the
 * response shape stays stable even if the persistence layer changes.
 */
@Getter
@Builder
public class PageResponse<T> {

    private List<T> content;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

    private boolean first;

    private boolean last;

    public static <T> PageResponse<T> of(Page<T> page) {

        return PageResponse.<T>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }

    public static <S, T> PageResponse<T> of(
            Page<S> page,
            Function<S, T> mapper) {

        return PageResponse.<T>builder()
                .content(page.getContent()
                        .stream()
                        .map(mapper)
                        .toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}
