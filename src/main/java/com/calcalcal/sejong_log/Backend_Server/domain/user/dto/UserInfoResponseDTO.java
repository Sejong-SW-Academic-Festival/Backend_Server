package com.calcalcal.sejong_log.Backend_Server.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponseDTO {
    private String name;
    private String email;
    private String department;
}