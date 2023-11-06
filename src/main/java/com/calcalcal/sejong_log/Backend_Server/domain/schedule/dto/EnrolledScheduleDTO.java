package com.calcalcal.sejong_log.Backend_Server.domain.schedule.dto;

import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.EnrolledSchedule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnrolledScheduleDTO {
    private Long id;
    private String name;
    private String description;
    private Long category_id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public static EnrolledScheduleDTO of(EnrolledSchedule schedule) {
        return new EnrolledScheduleDTO(
                schedule.getSchedule().getId(),
                schedule.getSchedule().getName(),
                schedule.getSchedule().getDescription(),
                schedule.getSchedule().getCategory().getId(),
                schedule.getSchedule().getStartDate(),
                schedule.getSchedule().getEndDate()
        );
    }
}