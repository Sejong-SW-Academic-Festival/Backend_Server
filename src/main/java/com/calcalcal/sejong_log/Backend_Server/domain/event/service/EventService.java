package com.calcalcal.sejong_log.Backend_Server.domain.event.service;

import com.calcalcal.sejong_log.Backend_Server.domain.category.dto.CategoryDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.category.entity.Category;
import com.calcalcal.sejong_log.Backend_Server.domain.category.repository.CategoryRepository;
import com.calcalcal.sejong_log.Backend_Server.domain.event.dao.EventRepository;
import com.calcalcal.sejong_log.Backend_Server.domain.event.dto.EventDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.event.dto.EventMakeDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.event.entity.Event;
import com.calcalcal.sejong_log.Backend_Server.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.calcalcal.sejong_log.Backend_Server.global.response.BaseResponseStatus.CATEGORY_NOT_EXIST;
import static com.calcalcal.sejong_log.Backend_Server.global.response.BaseResponseStatus.DATABASE_INSERT_ERROR;

@RequiredArgsConstructor
@Transactional
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    public void addEvent(EventMakeDTO eventMakeDTO) throws BaseException {
        String eventName = eventMakeDTO.getName();
        String eventDescription = eventMakeDTO.getDescription();
        String categoryName = eventMakeDTO.getCategoryName();
        LocalDateTime eventStartDate = eventMakeDTO.getStartDate();
        LocalDateTime eventEndDate = eventMakeDTO.getEndDate();

        Category category = categoryRepository.findCategoryByName(categoryName)
                .orElseThrow(() -> new BaseException(CATEGORY_NOT_EXIST));

        Event newEvent = Event.builder()
                .name(eventName)
                .description(eventDescription)
                .category(category)
                .startDate(eventStartDate)
                .endDate(eventEndDate)
                .build();

        try {
            eventRepository.save(newEvent);
        } catch (Exception e) {
            throw new BaseException(DATABASE_INSERT_ERROR);
        }
    }

    public void removeEvent() {
        // TODO: Implement removing event.
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

    public List<EventDTO> getEventByCategoryName(String categoryName) throws BaseException {
        List<String> childCategoryNames = new ArrayList<>();

        CategoryDTO categoryDTO = CategoryDTO.of(
                categoryRepository.findCategoryByName(categoryName)
                        .orElseThrow(() -> new BaseException(CATEGORY_NOT_EXIST))
        );

        preorder_traverse(categoryDTO, childCategoryNames);

        return eventRepository.getAllByCategoryNotNull().stream()
                .filter(event -> childCategoryNames.contains(event.getCategory().getName())).map(EventDTO::of).toList();
    }
}
