package com.calcalcal.sejong_log.Backend_Server.domain.user.repository;

import com.calcalcal.sejong_log.Backend_Server.domain.event.entity.Event;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.BookedEvent;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookedEventRepository extends JpaRepository<BookedEvent, Long> {
     Optional<BookedEvent> findBookedEventByUserAndEventOrderByEvent_StartDateAsc(User user, Event event);
}