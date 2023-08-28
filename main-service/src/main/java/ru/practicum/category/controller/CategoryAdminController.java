package ru.practicum.category.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;
import ru.practicum.category.service.ICategoryService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping(path = "/admin/categories")
public class CategoryAdminController {
    private final ICategoryService categoryService;

    @Autowired
    public CategoryAdminController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Category createNewCategory(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Пришел запрос  Post Category CategoryDto: {}", categoryDto);
        Category category = categoryService.createNewCategory(categoryDto);
        log.info("Отправлен ответ: {}", category);
        return category;
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeCategory(@PathVariable Long catId) {
        log.info("Пришел запрос Delete catId: {}", catId);
        categoryService.removeCategory(catId);
        log.info("Категория удалена");
    }

    @PatchMapping("/{catId}")
    public Category updateCategory(@PathVariable Long catId, @Valid @RequestBody CategoryDto categoryDto) {
        log.info("Пришел запрос patch catId: {}, CategoryDto:{}", catId, categoryDto);
        Category category = categoryService.updateCategory(catId, categoryDto);
        log.info("Отправлен ответ: {}", category);
        return category;
    }
}
