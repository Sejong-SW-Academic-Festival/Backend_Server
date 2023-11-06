package com.calcalcal.sejong_log.Backend_Server.domain.category.repository;

import com.calcalcal.sejong_log.Backend_Server.domain.category.common.CategoryType;
import com.calcalcal.sejong_log.Backend_Server.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> getAllByParentCategoryIsNull();
    Optional<Category> findCategoryById(Long id);
    Optional<Category> findCategoryByName(String name);
    List<Category> findCategoryByNameStartsWithAndCategoryTypeIs(String name, CategoryType type);

}
