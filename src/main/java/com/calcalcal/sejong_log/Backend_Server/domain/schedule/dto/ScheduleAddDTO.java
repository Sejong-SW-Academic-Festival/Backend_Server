package com.calcalcal.sejong_log.Backend_Server.domain.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleAddDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String categoryName;

    @JsonFormat
    private LocalDateTime startDate;

    @JsonFormat
    private LocalDateTime endDate;
}
