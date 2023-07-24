package ru.practicum.mapper;


import ru.practicum.model.Hit;
import ru.practicum.model.HitDto;


public class HitMapper {
    public static HitDto toHitDto(Hit hit) {
        return new HitDto(hit.getApp(),
                hit.getUri(), null);
    }
}
