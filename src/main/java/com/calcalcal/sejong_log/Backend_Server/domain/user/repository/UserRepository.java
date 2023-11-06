package com.calcalcal.sejong_log.Backend_Server.domain.user.repository;

import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
