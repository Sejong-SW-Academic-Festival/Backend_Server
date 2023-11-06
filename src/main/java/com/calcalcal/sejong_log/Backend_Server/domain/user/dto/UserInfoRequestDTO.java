package com.calcalcal.sejong_log.Backend_Server.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserInfoRequestDTO {
    @NotBlank private String name;
    @NotBlank private String department;
}