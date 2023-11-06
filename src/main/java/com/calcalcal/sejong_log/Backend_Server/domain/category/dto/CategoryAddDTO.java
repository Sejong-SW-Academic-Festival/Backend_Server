package com.calcalcal.sejong_log.Backend_Server.domain.category.dto;

import com.calcalcal.sejong_log.Backend_Server.domain.category.common.CategoryType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryAddDTO {
    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    private Long parentId;
}
