package com.calcalcal.sejong_log.Backend_Server.domain.user.service;

import com.calcalcal.sejong_log.Backend_Server.domain.category.entity.Category;
import com.calcalcal.sejong_log.Backend_Server.domain.category.repository.CategoryRepository;
import com.calcalcal.sejong_log.Backend_Server.domain.schedule.dao.ScheduleRepository;
import com.calcalcal.sejong_log.Backend_Server.domain.schedule.dto.BookedSchduleDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.schedule.dto.EnrolledScheduleDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.schedule.entity.Schedule;
import com.calcalcal.sejong_log.Backend_Server.domain.user.dto.LoginRequestDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.user.dto.SignupRequestDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.user.dto.UserInfoRequestDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.user.dto.UserInfoResponseDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.BookedSchedule;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.EnrolledSchedule;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.SubscribedCategory;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.User;
import com.calcalcal.sejong_log.Backend_Server.domain.user.repository.BookedScheduleRepository;
import com.calcalcal.sejong_log.Backend_Server.domain.user.repository.EnrolledScheduleRepository;
import com.calcalcal.sejong_log.Backend_Server.domain.user.repository.SubscribedCategoryRepository;
import com.calcalcal.sejong_log.Backend_Server.domain.user.repository.UserRepository;
import com.calcalcal.sejong_log.Backend_Server.global.exception.BaseException;
import com.calcalcal.sejong_log.Backend_Server.global.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static com.calcalcal.sejong_log.Backend_Server.global.properties.JwtProperties.*;
import static com.calcalcal.sejong_log.Backend_Server.global.response.BaseResponseStatus.*;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final EnrolledScheduleRepository enrolledScheduleRepository;
    private final CategoryRepository categoryRepository;
    private final SubscribedCategoryRepository subscribedCategoryRepository;
    private final ScheduleRepository scheduleRepository;
    private final BookedScheduleRepository bookedScheduleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;
    private final RedisTemplate<String, String> redisTemplate;

    public void signup(SignupRequestDTO signupRequestDTO) throws BaseException {
        userRepository.findUserByEmail(signupRequestDTO.getEmail())
                .ifPresent(s -> {
                    throw new BaseException(EMAIL_EXIST);
                });

        User userEntity = User.builder()
                .name(signupRequestDTO.getName())
                .email(signupRequestDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(signupRequestDTO.getPassword()))
                .department(signupRequestDTO.getDepartment())
                .build();

        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            throw new BaseException(DATABASE_INSERT_ERROR);
        }
    }

    public void login(LoginRequestDTO loginRequestDTO, HttpServletResponse response) throws BaseException {
        User user = userRepository.findUserByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new BaseException(USER_NOT_EXIST));

        if (!bCryptPasswordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new BaseException(WRONG_PASSWORD);
        }

        Map<String, String> token = jwtUtils.generateAccessTokenWithRefreshToken(user);

        response.addHeader(JWT_ACCESS_TOKEN_HEADER_NAME, JWT_ACCESS_TOKEN_TYPE + token.get(ACCESS_TOKEN));

        Cookie cookie = new Cookie(REFRESH_TOKEN, token.get(REFRESH_TOKEN));
        cookie.setMaxAge((int) REFRESH_TOKEN_EXPIRE_TIME);
        cookie.setPath("/");

        response.addCookie(cookie);

        Duration duration = Duration.of(ACCESS_TOKEN_EXPIRE_TIME, ChronoUnit.MILLIS);
        redisTemplate.opsForValue().set(JWT_TOKEN + user.getEmail(), token.get(ACCESS_TOKEN), duration);
    }

    public void logout(HttpServletRequest request) throws BaseException {
        User user = getUser(request);
        String userEmail = user.getEmail();

        if (redisTemplate.opsForValue().get(JWT_TOKEN + userEmail) != null) {
            redisTemplate.delete(JWT_TOKEN + userEmail);
        }
    }

    public void withDraw(HttpServletRequest request, String password) throws BaseException {
        User user = getUser(request);

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new BaseException(WRONG_PASSWORD);
        }

        logout(request);

        try {
            userRepository.delete(user);
        } catch (BaseException e) {
            throw new BaseException(DATABASE_DELETE_ERROR);
        }
    }

    public List<EnrolledScheduleDTO> getEnrolledSchedules(HttpServletRequest request) {
        User user = getUser(request);

        return enrolledScheduleRepository.getEnrolledSchedulesByUserOrderBySchedule_StartDateAsc(user).
                stream().map(EnrolledScheduleDTO::of).toList();
    }

    public void subscribeCategory(HttpServletRequest request, String categoryName) throws BaseException {
        User user = getUser(request);

        Category category = categoryRepository.findCategoryByName(categoryName)
                .orElseThrow(() -> new BaseException(CATEGORY_NOT_EXIST));

        subscribedCategoryRepository.findSubscribedCategoryByUserAndCategory(user, category)
                .ifPresent(s -> {
                    throw new BaseException(ALREADY_SUBSCRIBED);
                });

        SubscribedCategory newSubscription = SubscribedCategory.builder()
                .user(user)
                .category(category)
                .build();

        try {
            subscribedCategoryRepository.save(newSubscription);
        } catch (Exception e) {
            throw new BaseException(DATABASE_INSERT_ERROR);
        }
    }

    public void unsubscribeCategory(HttpServletRequest request, String categoryName) {
        User user = getUser(request);

        Category category = categoryRepository.findCategoryByName(categoryName)
                .orElseThrow(() -> new BaseException(SCHEDULE_NOT_EXIST));

        SubscribedCategory subscribedCategory = subscribedCategoryRepository.findSubscribedCategoryByUserAndCategory(user, category)
                .orElseThrow(() -> new BaseException(NOT_SUBSCRIBED));

        try {
            subscribedCategoryRepository.delete(subscribedCategory);
        } catch (Exception e) {
            throw new BaseException(DATABASE_DELETE_ERROR);
        }
    }

    public void enrollSchedule(HttpServletRequest request, String scheduleName) throws BaseException {
        User user = getUser(request);

        Schedule schedule = scheduleRepository.findScheduleByName(scheduleName)
                .orElseThrow(() -> new BaseException(SCHEDULE_NOT_EXIST));

        enrolledScheduleRepository.findEnrolledScheduleByUserAndSchedule(user, schedule)
                .ifPresent(s -> {
                    throw new BaseException(ALREADY_ENROLLED);
                });

        EnrolledSchedule enrolledSchedule = EnrolledSchedule.builder()
                .user(user)
                .schedule(schedule)
                .build();

        try {
            enrolledScheduleRepository.save(enrolledSchedule);
        } catch (Exception e) {
            throw new BaseException(DATABASE_INSERT_ERROR);
        }
    }

    public void withdrawSchedule(HttpServletRequest request, String scheduleName) throws BaseException {
        User user = getUser(request);

        Schedule schedule = scheduleRepository.findScheduleByName(scheduleName)
                .orElseThrow(() -> new BaseException(SCHEDULE_NOT_EXIST));

        EnrolledSchedule enrolledSchedule = enrolledScheduleRepository.findEnrolledScheduleByUserAndSchedule(user, schedule)
                .orElseThrow(() -> new BaseException(NOT_ENROLLED));

        try {
            enrolledScheduleRepository.delete(enrolledSchedule);
        } catch (Exception e) {
            throw new BaseException(DATABASE_DELETE_ERROR);
        }
    }

    public void bookSchedule(HttpServletRequest request, String scheduleName) throws BaseException {
        User user = getUser(request);

        Schedule schedule = scheduleRepository.findScheduleByName(scheduleName)
                .orElseThrow(() -> new BaseException(SCHEDULE_NOT_EXIST));

        bookedScheduleRepository.findBookedScheduleByUserAndSchedule(user, schedule)
                .ifPresent(s -> {
                    throw new BaseException(ALREADY_BOOKED);
                });

        BookedSchedule bookedSchedule = BookedSchedule.builder()
                .user(user)
                .schedule(schedule)
                .build();

        try {
            bookedScheduleRepository.save(bookedSchedule);
        } catch (Exception e) {
            throw new BaseException(DATABASE_INSERT_ERROR);
        }
    }

    public void unbookSchedule(HttpServletRequest request, String scheduleName) throws BaseException {
        User user = getUser(request);

        Schedule schedule = scheduleRepository.findScheduleByName(scheduleName)
                .orElseThrow(() -> new BaseException(SCHEDULE_NOT_EXIST));

        BookedSchedule bookedSchedule = bookedScheduleRepository.findBookedScheduleByUserAndSchedule(user, schedule)
                .orElseThrow(() -> new BaseException(NOT_BOOKED));

        try {
            bookedScheduleRepository.delete(bookedSchedule);
        } catch (Exception e) {
            throw new BaseException(DATABASE_DELETE_ERROR);
        }
    }

    public List<BookedSchduleDTO> getBookedSchedule(HttpServletRequest request) {
        User user = getUser(request);

        return bookedScheduleRepository.findBookedScheduleByUserOrderBySchedule_StartDateAsc(user)
                .stream().map(BookedSchduleDTO::of).toList();
    }

    private User getUser(HttpServletRequest request) throws BaseException {
        String jwtHeader = request.getHeader(JWT_ACCESS_TOKEN_HEADER_NAME);

        if (jwtHeader == null || !jwtHeader.startsWith(JWT_ACCESS_TOKEN_TYPE)) {
            throw new BaseException(NO_JWT_TOKEN);
        }

        String jwtToken = jwtHeader.replace(JWT_ACCESS_TOKEN_TYPE, EMPTY_STRING);
        String userEmail = jwtUtils.getUserEmail(jwtToken);

        return userRepository.findUserByEmail(userEmail).orElseThrow(
                () -> new BaseException(USER_NOT_EXIST)
        );
    }

    public UserInfoResponseDTO getUserInfo(HttpServletRequest request) throws BaseException {
        User user = getUser(request);

        return UserInfoResponseDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .department(user.getDepartment())
                .build();
    }

    public void updateUserInfo(HttpServletRequest request, UserInfoRequestDTO userInfoRequest) throws BaseException {
        User user = getUser(request);

        user.setName(userInfoRequest.getName());
        user.setDepartment(userInfoRequest.getDepartment());

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new BaseException(DATABASE_INSERT_ERROR);
        }
    }
}
