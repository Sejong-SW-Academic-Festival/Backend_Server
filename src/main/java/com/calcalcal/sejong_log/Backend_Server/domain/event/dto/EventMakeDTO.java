package com.calcalcal.sejong_log.Backend_Server.domain.event.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class EventMakeDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String categoryName;

    @NotBlank @DateTimeFormat
    private LocalDateTime startDate;

    @NotBlank @DateTimeFormat
    private LocalDateTime endDate;
}
