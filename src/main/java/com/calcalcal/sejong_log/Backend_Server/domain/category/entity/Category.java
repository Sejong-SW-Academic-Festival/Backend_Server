package com.calcalcal.sejong_log.Backend_Server.domain.category.entity;

import com.calcalcal.sejong_log.Backend_Server.domain.category.common.CategoryType;
import com.calcalcal.sejong_log.Backend_Server.domain.schedule.entity.Schedule;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.SubscribedCategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType categoryType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCategory")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> childCategory = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<SubscribedCategory> subscribedUsers;

    @OneToMany(mappedBy = "category")
    private List<Schedule> schedules;

    @OneToOne(mappedBy = "category")
    private Department department;

    @Builder
    public Category(String name, String categoryType, Category parentCategory) {
        this.name = name;
        this.categoryType = CategoryType.valueOf(categoryType);
        this.parentCategory = parentCategory;
    }
}
