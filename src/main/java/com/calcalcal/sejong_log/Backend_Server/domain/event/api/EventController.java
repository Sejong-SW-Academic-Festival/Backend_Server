package com.calcalcal.sejong_log.Backend_Server.domain.event.api;

import com.calcalcal.sejong_log.Backend_Server.domain.event.service.EventService;
import com.calcalcal.sejong_log.Backend_Server.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    @GetMapping("/get-list/{name}")
    public BaseResponse<?> getEventsByCategoryName(@PathVariable String name) {
        return new BaseResponse<>(eventService.getEventByCategoryName(name));
    }
}
