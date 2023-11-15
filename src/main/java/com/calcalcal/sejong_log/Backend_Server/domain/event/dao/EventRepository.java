package com.calcalcal.sejong_log.Backend_Server.domain.event.dao;

import com.calcalcal.sejong_log.Backend_Server.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findEventByName(String name);
    List<Event> getAllByCategoryNotNull();
}
