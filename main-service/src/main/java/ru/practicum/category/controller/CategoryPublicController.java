package ru.practicum.category.controller;

import com.sun.nio.sctp.IllegalReceiveException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.model.Category;
import ru.practicum.category.service.ICategoryService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/categories")
public class CategoryPublicController {
    private final ICategoryService categoryService;

    @Autowired
    public CategoryPublicController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getCategories(@RequestParam(value = "from", defaultValue = "0", required = false) Integer from,
                                        @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {

        if (size <= 0 || from < 0) {
            throw new IllegalReceiveException("Неверно указан параметр");
        }
        return categoryService.findAllCategory(from, size);
    }

    @GetMapping("/{catId}")
    public Category getCategoryById(@PathVariable Long catId) {
        log.info("Пришел запрос Get Id: {}", catId);
        Category category = categoryService.getById(catId);
        log.info("Отправлен ответ: {}", category);
        return category;
    }
}
