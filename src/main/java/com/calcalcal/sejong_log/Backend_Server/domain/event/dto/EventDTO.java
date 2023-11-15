package com.calcalcal.sejong_log.Backend_Server.domain.event.dto;

import com.calcalcal.sejong_log.Backend_Server.domain.event.entity.Event;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventDTO {
    private Long id;
    private String name;
    private String description;
    private Long category_id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public static EventDTO of(Event event) {
        return new EventDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getCategory().getId(),
                event.getStartDate(),
                event.getEndDate()
        );
    }
}
