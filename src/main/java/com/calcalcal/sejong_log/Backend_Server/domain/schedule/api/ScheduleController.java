package com.calcalcal.sejong_log.Backend_Server.domain.schedule.api;

import com.calcalcal.sejong_log.Backend_Server.domain.schedule.dto.ScheduleAddDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.schedule.service.ScheduleService;
import com.calcalcal.sejong_log.Backend_Server.global.exception.BaseException;
import com.calcalcal.sejong_log.Backend_Server.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/get-list-in-{categoryName}")
    public BaseResponse<?> getSchedulesByCategoryName(@PathVariable String categoryName) {
        try {
            return new BaseResponse<>(scheduleService.getScheduleByCategoryName(categoryName));
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/get-list")
    public BaseResponse<?> getAllSchedules() {
        return new BaseResponse<>(scheduleService.getAllSchedules());
    }

    @PostMapping("/add")
    public BaseResponse<?> addSchedule(@Valid @RequestBody ScheduleAddDTO scheduleAddDTO, BindingResult result) {
        if (result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();
            return new BaseResponse<>(false, 400, message);
        }

        try {
            scheduleService.addSchedule(scheduleAddDTO);
            return new BaseResponse<>("성공적으로 등록했습니다!");
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
