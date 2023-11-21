package com.calcalcal.sejong_log.Backend_Server.domain.schedule.dto;

import com.calcalcal.sejong_log.Backend_Server.domain.schedule.entity.Schedule;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.EnrolledSchedule;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleDTO {
    private Long id;
    private String name;
    private String description;
    private String location;
    private Long category_id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> enrolledUserEmail;

    public static ScheduleDTO of(Schedule schedule) {
        return new ScheduleDTO(
                schedule.getId(),
                schedule.getName(),
                schedule.getDescription(),
                schedule.getLocation(),
                schedule.getCategory().getId(),
                schedule.getStartDate(),
                schedule.getEndDate(),
                schedule.getEnrolledUsers().stream().map(EnrolledSchedule::of)
                        .map(EnrolledSchedule::getUser)
                        .map(User::getEmail)
                        .collect(Collectors.toList())
        );
    }
}
