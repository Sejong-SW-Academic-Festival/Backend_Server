package com.calcalcal.sejong_log.Backend_Server.domain.category.dto;

import com.calcalcal.sejong_log.Backend_Server.domain.category.common.CategoryType;
import com.calcalcal.sejong_log.Backend_Server.domain.category.entity.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SingleCategoryDTO {
    private Long id;
    private String name;
    private CategoryType categoryType;

    public static SingleCategoryDTO of(Category category) {
        return new SingleCategoryDTO(
                category.getId(),
                category.getName(),
                category.getCategoryType()
        );
    }
}
