package com.calcalcal.sejong_log.Backend_Server.domain.user.repository;

import com.calcalcal.sejong_log.Backend_Server.domain.category.entity.Category;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.SubscribedCategory;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscribedCategoryRepository extends JpaRepository<SubscribedCategory, Long> {
    List<SubscribedCategory> findSubscribedCategoriesByUser(User user);
    Optional<SubscribedCategory> findSubscribedCategoryByUserAndCategory(User user, Category category);
}
