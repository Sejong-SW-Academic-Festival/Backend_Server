package com.calcalcal.sejong_log.Backend_Server.domain.user.repository;

import com.calcalcal.sejong_log.Backend_Server.domain.schedule.entity.Schedule;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.BookedSchedule;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookedScheduleRepository extends JpaRepository<BookedSchedule, Long> {
     Optional<BookedSchedule> findBookedScheduleByUserAndSchedule(User user, Schedule schedule);
     List<BookedSchedule> findBookedScheduleByUserOrderBySchedule_StartDateAsc(User user);
     List<BookedSchedule> findBookedScheduleByUser(User user);
}
