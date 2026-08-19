package com.guyo.archive_system.category.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guyo.archive_system.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findByDeletedAtIsNull();

    Optional<Category> findByCategoryIdAndDeletedAtIsNull(UUID categoryId);

}