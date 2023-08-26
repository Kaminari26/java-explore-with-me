package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;

import java.util.List;

public interface ICategoryService {
    Category createNewCategory(CategoryDto categoryDto);

    void removeCategory(Long id);

    Category updateCategory(Long id, CategoryDto categoryDto);

    List<Category> findAllCategory(int from, int size);

    Category getById(Long id);
}
