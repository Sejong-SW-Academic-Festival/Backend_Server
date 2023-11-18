package com.calcalcal.sejong_log.Backend_Server.domain.schedule.dao;

import com.calcalcal.sejong_log.Backend_Server.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findScheduleByName(String name);
    List<Schedule> getAllByCategoryNotNull();

    List<Schedule> findAllByOrderByStartDateAsc();
}
