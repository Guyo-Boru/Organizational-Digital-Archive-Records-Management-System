package com.guyo.archive_system.category.service;

import java.util.List;
import java.util.UUID;

import com.guyo.archive_system.category.dto.CategoryDto;

public interface CategoryService {

    CategoryDto getById(UUID categoryId);

    List<CategoryDto> getAll();

}