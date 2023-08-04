package ru.practicum.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private String name;
}
