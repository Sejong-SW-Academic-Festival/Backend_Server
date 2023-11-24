package com.calcalcal.sejong_log.Backend_Server.domain.user.api;

import com.calcalcal.sejong_log.Backend_Server.domain.schedule.dto.ScheduleAddDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.user.dto.LoginRequestDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.user.dto.SignupRequestDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.user.dto.UserInfoRequestDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.user.dto.UserInfoResponseDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.user.service.UserService;
import com.calcalcal.sejong_log.Backend_Server.global.exception.BaseException;
import com.calcalcal.sejong_log.Backend_Server.global.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public BaseResponse<String> signup(@Valid @RequestBody SignupRequestDTO signupRequestDTO, BindingResult result) {
        if (result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();
            return new BaseResponse<>(false, 400, message);
        }

        try {
            userService.signup(signupRequestDTO);
            return new BaseResponse<>("회원가입에 성공하였습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    @PostMapping("/login")
    public BaseResponse<String> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();
            return new BaseResponse<>(false, 400, message);
        }

        try {
            userService.login(loginRequestDTO, response);
            return new BaseResponse<>("로그인에 성공하였습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/logout")
    public BaseResponse<String> logout(HttpServletRequest request) {
        try {
            userService.logout(request);
            return new BaseResponse<>("로그아웃에 성공하였습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/mypage")
    public BaseResponse<UserInfoResponseDTO> getMyPage(HttpServletRequest request) {
        try {
            UserInfoResponseDTO userInfoDTO = userService.getUserInfo(request);
            return new BaseResponse<>(userInfoDTO);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping("/update")
    public BaseResponse<String> updateProfile(HttpServletRequest request, @RequestBody UserInfoRequestDTO userInfoRequest) {
        try {
            userService.updateUserInfo(request, userInfoRequest);
            return new BaseResponse<>("프로필을 수정하였습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/withdraw")
    public BaseResponse<String> withdraw(@Valid @RequestBody String password, HttpServletRequest request) {
        try {
            userService.withDraw(request, password);
            return new BaseResponse<>("회원탈퇴에 성공했습니다");
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/add-personal-schedule")
    public BaseResponse<?> addPsersonalSchedule(HttpServletRequest request, @Valid @RequestBody ScheduleAddDTO scheduleAddDTO, BindingResult result) {
        if (result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();
            return new BaseResponse<>(false, 400, message);
        }

        try {
            userService.addSchedule(request, scheduleAddDTO);
            userService.enrollSchedule(request, scheduleAddDTO.getName());
            return new BaseResponse<>("성공적으로 등록했습니다!");
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/get-schedules")
    public BaseResponse<?> getSchedules(HttpServletRequest request) {
        try {
            return new BaseResponse<>(userService.getAllSchedulesInSubscribedCategories(request));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/get-enrolled-schedules")
    public BaseResponse<?> getEnrolledSchedules(HttpServletRequest request) {
        try {
            return new BaseResponse<>(userService.getEnrolledSchedules(request));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PutMapping("/subscribe-category/{categoryName}")
    public BaseResponse<?> subscribeCategory(HttpServletRequest request, @PathVariable String categoryName) {
        try {
            userService.subscribeCategory(request, categoryName);
            return new BaseResponse<>("성공적으로 추가했습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PutMapping("/unsubscribe-category/{categoryName}")
    public BaseResponse<?> unsubscribeCategory(HttpServletRequest request, @PathVariable String categoryName) {
        try {
            userService.unsubscribeCategory(request, categoryName);
            return new BaseResponse<>("성공적으로 취소했습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/get-subscribed-category")
    public BaseResponse<?> getSubscribeCategory(HttpServletRequest request) {
        try {
            return new BaseResponse<>(userService.getSubscribeCategory(request));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/get-all-category-list")
    public BaseResponse<?> getAllCategories(HttpServletRequest request) {
        try {
            return new BaseResponse<>(userService.getAllCategoriesWithUserSubscribedOrNot(request));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PutMapping("/enroll-schedule/{scheduleName}")
    public BaseResponse<?> enrollSchedule(HttpServletRequest request, @PathVariable String scheduleName) {
        try {
            userService.enrollSchedule(request, scheduleName);
            return new BaseResponse<>("성공적으로 추가했습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PutMapping("/withdraw-schedule/{scheduleName}")
    public BaseResponse<?> withdrawSchedule(HttpServletRequest request, @PathVariable String scheduleName) {
        try {
            userService.withdrawSchedule(request, scheduleName);
            return new BaseResponse<>("성공적으로 취소했습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /*
    @PutMapping("/book-schedule/{scheduleName}")
    public BaseResponse<?> bookSchedule(HttpServletRequest request, @PathVariable String scheduleName) {
        try {
            userService.bookSchedule(request, scheduleName);
            return new BaseResponse<>("성공적으로 추가했습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PutMapping("/unbook-schedule/{scheduleName}")
    public BaseResponse<?> unbookSchedule(HttpServletRequest request, @PathVariable String scheduleName) {
        try {
            userService.unbookSchedule(request, scheduleName);
            return new BaseResponse<>("성공적으로 취소했습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    @GetMapping("/get-booked-schedules")
    public BaseResponse<?> getBookedSchedule(HttpServletRequest request) {
        try {
            return new BaseResponse<>(userService.getBookedSchedule(request));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    */
}
