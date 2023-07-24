package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class HitDto {
    private String app;
    private String uri;
    private Long hits;
}
