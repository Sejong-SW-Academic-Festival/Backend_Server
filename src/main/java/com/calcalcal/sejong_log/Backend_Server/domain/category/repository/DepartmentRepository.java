package com.calcalcal.sejong_log.Backend_Server.domain.category.repository;

import com.calcalcal.sejong_log.Backend_Server.domain.category.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}