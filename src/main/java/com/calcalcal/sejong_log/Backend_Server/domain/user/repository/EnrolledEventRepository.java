package com.calcalcal.sejong_log.Backend_Server.domain.user.repository;

import com.calcalcal.sejong_log.Backend_Server.domain.event.entity.Event;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.EnrolledEvent;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrolledEventRepository extends JpaRepository<EnrolledEvent, Long> {
     Optional<EnrolledEvent> findEnrolledEventByUserAndEvent(User user, Event event);
     List<EnrolledEvent> getEnrolledEventsByUserOrderByEvent_StartDateAsc(User user);
}
