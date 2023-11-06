package com.calcalcal.sejong_log.Backend_Server.domain.category.api;

import com.calcalcal.sejong_log.Backend_Server.domain.category.dto.CategoryAddDTO;
import com.calcalcal.sejong_log.Backend_Server.domain.category.service.CategoryService;
import com.calcalcal.sejong_log.Backend_Server.global.exception.BaseException;
import com.calcalcal.sejong_log.Backend_Server.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/get-list")
    public BaseResponse<?> getList() {
        return new BaseResponse<>(categoryService.getAllCategoryList());
    }

    @GetMapping("/get-department-list")
    public BaseResponse<?> getDepartmentList() {
        return new BaseResponse<>(categoryService.getAllDepartmentList());
    }

    @PostMapping("/add")
    public BaseResponse<String> addCategory(@RequestBody @Valid CategoryAddDTO categoryAddDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String message = bindingResult.getFieldError().getDefaultMessage();
            return new BaseResponse<>(false, 400, message);
        }

        try {
            categoryService.addCategory(categoryAddDTO);
            return new BaseResponse<>("카테고리 생성에 성공하였습니다.");
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @DeleteMapping("/delete/{categoryName}")
    public BaseResponse<String> removeCategory(@PathVariable String categoryName) {
        try {
            categoryService.removeCategory(categoryName);
            return new BaseResponse<>("카테고리 제거에 성공했습니다.");
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}