package com.calcalcal.sejong_log.Backend_Server.domain.category.service;

import com.calcalcal.sejong_log.Backend_Server.domain.category.common.CategoryType;
import com.calcalcal.sejong_log.Backend_Server.domain.category.dto.CategoryAddDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.category.dto.CategoryDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.category.entity.Category;
import com.calcalcal.sejong_log.Backend_Server.domain.category.entity.Department;
import com.calcalcal.sejong_log.Backend_Server.domain.category.repository.CategoryRepository;
import com.calcalcal.sejong_log.Backend_Server.domain.category.repository.DepartmentRepository;
import com.calcalcal.sejong_log.Backend_Server.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.calcalcal.sejong_log.Backend_Server.global.response.BaseResponseStatus.*;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final DepartmentRepository departmentRepository;

    public List<CategoryDTO> getAllCategoryList() {
        return categoryRepository.getAllByParentCategoryIsNull().stream().map(CategoryDTO::of).collect(Collectors.toList());
    }

    public List<CategoryDTO> getAllDepartmentList() {
        return categoryRepository.getAllByParentCategoryIsNull().stream()
                .filter(category -> category.getCategoryType() == CategoryType.DEPARTMENT)
                .map(CategoryDTO::ofDepartment).collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) throws BaseException {
        return CategoryDTO.of(categoryRepository.findCategoryById(id)
                .orElseThrow(() -> new BaseException(CATEGORY_NOT_EXIST))
        );
    }

    public CategoryDTO getCategoryByName(String name) {
        return CategoryDTO.of(categoryRepository.findCategoryByName(name)
                .orElseThrow(() -> new BaseException(CATEGORY_NOT_EXIST))
        );
    }

    public void addCategory(CategoryAddDTO categoryAddDTO) throws BaseException {
        String name = categoryAddDTO.getName();
        String categoryType = String.valueOf(categoryAddDTO.getCategoryType());
        Long parentCategoryId = categoryAddDTO.getParentId();

        Category parentCategory = null;

        if(parentCategoryId != null)
            parentCategory = categoryRepository.findCategoryById(parentCategoryId)
                    .orElseThrow(() -> new BaseException(CATEGORY_NOT_EXIST));

        categoryRepository.findCategoryByName(name)
                .ifPresent(s -> {
                    throw new BaseException(CATEGORY_EXIST);
                });

        Category newCategory = Category.builder()
                .name(name)
                .categoryType(categoryType)
                .parentCategory(parentCategory)
                .build();

        try {
            categoryRepository.save(newCategory);
        } catch(Exception e) {
            throw new BaseException(DATABASE_INSERT_ERROR);
        }

        if(CategoryType.valueOf(categoryType) != CategoryType.DEPARTMENT) {
            return;
        }

        Department newDepartment = Department.builder()
                .name(name)
                .category(newCategory)
                .build();

        try {
            departmentRepository.save(newDepartment);
        } catch(Exception e) {
            throw new BaseException(DATABASE_INSERT_ERROR);
        }
    }

    public void removeCategory(String name) {
        Category category = categoryRepository.findCategoryByName(name)
                .orElseThrow(() -> new BaseException(CATEGORY_NOT_EXIST));

        try {
            categoryRepository.delete(category);
        } catch (Exception e) {
            throw new BaseException(DATABASE_DELETE_ERROR);
        }
    }
}
