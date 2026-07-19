package com.guyo.archive_system.category.mapper;

import org.springframework.stereotype.Component;

import com.guyo.archive_system.category.dto.CategoryDto;
import com.guyo.archive_system.category.entity.Category;

@Component
public class CategoryMapper {

    public CategoryDto toDto(Category category) {

        if (category == null) {
            return null;
        }

        return CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .parentCategoryId(category.getParentCategoryId())
                .retentionPeriodMonths(category.getRetentionPeriodMonths())
                .build();
    }
}