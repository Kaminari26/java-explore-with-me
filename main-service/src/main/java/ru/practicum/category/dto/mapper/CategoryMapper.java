package ru.practicum.category.dto.mapper;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;

public class CategoryMapper {
    public static Category toDtoCategory(CategoryDto categoryDto) {
        return new Category(null, categoryDto.getName());
    }

    public static CategoryDto toDtoCategory(Category category) {
        return new CategoryDto(category.getName());
    }
}
