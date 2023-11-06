package com.calcalcal.sejong_log.Backend_Server.domain.user.repository;

import com.calcalcal.sejong_log.Backend_Server.domain.schedule.entity.Schedule;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.EnrolledSchedule;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrolledScheduleRepository extends JpaRepository<EnrolledSchedule, Long> {
     Optional<EnrolledSchedule> findEnrolledScheduleByUserAndSchedule(User user, Schedule schedule);
     List<EnrolledSchedule> getEnrolledSchedulesByUser(User user);
}
