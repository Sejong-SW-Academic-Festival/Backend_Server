package com.calcalcal.sejong_log.Backend_Server.domain.category.dto;

import com.calcalcal.sejong_log.Backend_Server.domain.category.common.CategoryType;
import com.calcalcal.sejong_log.Backend_Server.domain.category.entity.Category;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryDTO {
    private Long id;
    private String name;
    private CategoryType categoryType;
    private List<CategoryDTO> children;
    @Setter
    private Boolean subscribed;

    public static CategoryDTO of(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getCategoryType(),
                category.getChildCategory().stream().map(CategoryDTO::of).collect(Collectors.toList()),
                false
        );
    }

    public static CategoryDTO ofDepartment(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getCategoryType(),
                category.getChildCategory().stream()
                        .filter(department -> department.getCategoryType() == CategoryType.DEPARTMENT)
                        .map(CategoryDTO::ofDepartment).collect(Collectors.toList()),
                false
        );
    }
}
