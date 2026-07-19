package com.guyo.archive_system.category.dto;

import java.util.UUID;

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
public class CategoryDto {

    private UUID categoryId;

    private String name;

    private String description;

    private UUID parentCategoryId;

    private Integer retentionPeriodMonths;

}