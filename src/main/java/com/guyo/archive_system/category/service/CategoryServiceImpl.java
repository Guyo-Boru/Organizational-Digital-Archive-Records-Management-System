package com.guyo.archive_system.category.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.guyo.archive_system.category.dto.CategoryDto;
import com.guyo.archive_system.category.entity.Category;
import com.guyo.archive_system.category.mapper.CategoryMapper;
import com.guyo.archive_system.category.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto getById(UUID categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> getAll() {

        return categoryRepository.findByDeletedAtIsNull()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }
}