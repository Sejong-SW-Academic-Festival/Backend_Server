package com.calcalcal.sejong_log.Backend_Server.domain.schedule.service;

import com.calcalcal.sejong_log.Backend_Server.domain.category.dto.CategoryDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.category.entity.Category;
import com.calcalcal.sejong_log.Backend_Server.domain.category.repository.CategoryRepository;
import com.calcalcal.sejong_log.Backend_Server.domain.schedule.dao.ScheduleRepository;
import com.calcalcal.sejong_log.Backend_Server.domain.schedule.dto.ScheduleAddDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.schedule.dto.ScheduleDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.schedule.entity.Schedule;
import com.calcalcal.sejong_log.Backend_Server.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.calcalcal.sejong_log.Backend_Server.global.response.BaseResponseStatus.*;

@RequiredArgsConstructor
@Transactional
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CategoryRepository categoryRepository;

    public void addSchedule(ScheduleAddDTO scheduleAddDTO) throws BaseException {
        String scheduleName = scheduleAddDTO.getName();
        String scheduleDescription = scheduleAddDTO.getDescription();
        String categoryName = scheduleAddDTO.getCategoryName();
        LocalDateTime scheduleStartDate = scheduleAddDTO.getStartDate();
        LocalDateTime scheduleEndDate = scheduleAddDTO.getEndDate();

        Category category = categoryRepository.findCategoryByName(categoryName)
                .orElseThrow(() -> new BaseException(CATEGORY_NOT_EXIST));

        scheduleRepository.findScheduleByName(scheduleName)
                .ifPresent(
                        s -> {
                            throw new BaseException(SCHEDULE_EXIST);
                        });

        Schedule newSchedule = Schedule.builder()
                .name(scheduleName)
                .description(scheduleDescription)
                .category(category)
                .startDate(scheduleStartDate)
                .endDate(scheduleEndDate)
                .build();

        try {
            scheduleRepository.save(newSchedule);
        } catch (Exception e) {
            throw new BaseException(DATABASE_INSERT_ERROR);
        }
    }


    private void preorder_traverse(CategoryDTO categoryDTO, List<String> childCategoryNames) {
        childCategoryNames.add(categoryDTO.getName());
        List<CategoryDTO> children = categoryDTO.getChildren();

        if(!categoryDTO.getChildren().isEmpty()) {
            for(CategoryDTO childCategoryDTO: children) {
                preorder_traverse(childCategoryDTO, childCategoryNames);
            }
        }
    }

    public List<ScheduleDTO> getScheduleByCategoryName(String categoryName) throws BaseException {
        List<String> childCategoryNames = new ArrayList<>();

        CategoryDTO categoryDTO = CategoryDTO.of(
                categoryRepository.findCategoryByName(categoryName)
                        .orElseThrow(() -> new BaseException(CATEGORY_NOT_EXIST))
        );

        preorder_traverse(categoryDTO, childCategoryNames);

        return scheduleRepository.getAllByCategoryNotNull().stream()
                .filter(schedule -> childCategoryNames.contains(schedule.getCategory().getName())).map(ScheduleDTO::of).toList();
    }

    public List<ScheduleDTO> getAllSchedules() {
        return scheduleRepository.findAllByOrderByStartDateAsc().stream()
                .map(ScheduleDTO::of).toList();
    }
}
