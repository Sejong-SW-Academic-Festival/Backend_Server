package com.calcalcal.sejong_log.Backend_Server.domain.user.entity;

import com.calcalcal.sejong_log.Backend_Server.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "subscribed_category")
public class SubscribedCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder
    public SubscribedCategory(User user, Category category) {
        this.user = user;
        this.category = category;
    }
}
