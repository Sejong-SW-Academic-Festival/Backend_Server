package com.calcalcal.sejong_log.Backend_Server.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserWithdrawDTO {
    @NotBlank private String password;
}
