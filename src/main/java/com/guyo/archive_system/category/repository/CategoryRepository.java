package com.guyo.archive_system.category.repository;

import com.guyo.archive_system.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findByDeletedAtIsNull();

}