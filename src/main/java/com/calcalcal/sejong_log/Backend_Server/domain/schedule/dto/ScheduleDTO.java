package com.calcalcal.sejong_log.Backend_Server.domain.schedule.dto;

import com.calcalcal.sejong_log.Backend_Server.domain.schedule.entity.Schedule;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.BookedSchedule;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.EnrolledSchedule;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleDTO {
    private Long id;
    private String name;
    private String description;
    private String location;
    private String categoryName;
    private String categoryType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> enrolledUserEmail;
    private boolean liked;

    public static ScheduleDTO of(Schedule schedule) {
        return new ScheduleDTO(
                schedule.getId(),
                schedule.getName(),
                schedule.getDescription(),
                schedule.getLocation(),
                schedule.getCategory().getName(),
                schedule.getCategory().getCategoryType().name(),
                schedule.getStartDate(),
                schedule.getEndDate(),
                schedule.getEnrolledUsers().stream().map(EnrolledSchedule::of)
                        .map(EnrolledSchedule::getUser)
                        .map(User::getEmail)
                        .collect(Collectors.toList()),
                schedule.isLiked()

        );
    }
}
