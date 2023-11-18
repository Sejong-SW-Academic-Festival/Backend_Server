package com.calcalcal.sejong_log.Backend_Server.domain.schedule.dto;

import com.calcalcal.sejong_log.Backend_Server.domain.schedule.entity.Schedule;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.BookedSchedule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookedSchduleDTO {
    private Long id;
    private String name;
    private String description;
    private Long category_id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public static BookedSchduleDTO of(BookedSchedule bookedSchedule) {
        return new BookedSchduleDTO(
                bookedSchedule.getSchedule().getId(),
                bookedSchedule.getSchedule().getName(),
                bookedSchedule.getSchedule().getDescription(),
                bookedSchedule.getSchedule().getCategory().getId(),
                bookedSchedule.getSchedule().getStartDate(),
                bookedSchedule.getSchedule().getEndDate()
        );
    }
}
