package com.calcalcal.sejong_log.Backend_Server.domain.category.dto;

import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.SubscribedCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubscribeCategoryDTO {
    private Long id;
    private String name;

    public static SubscribeCategoryDTO of(SubscribedCategory subscribedCategory) {
        return new SubscribeCategoryDTO(
                subscribedCategory.getCategory().getId(),
                subscribedCategory.getCategory().getName()
        );
    }
}
