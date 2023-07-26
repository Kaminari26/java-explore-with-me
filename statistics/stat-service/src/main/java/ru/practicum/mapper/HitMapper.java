package ru.practicum.mapper;

import ru.practicum.model.Hit;
import ru.practicum.model.HitDto;
import ru.practicum.model.HitDtoRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class HitMapper {
    public static HitDto toHitDto(Hit hit) {
        return new HitDto(hit.getApp(),
                hit.getUri(),
                null);
    }

    public static Hit toHitDto(HitDtoRequest hitDtoRequest) {
        return new Hit(null,
                hitDtoRequest.getApp(),
                hitDtoRequest.getUri(),
                hitDtoRequest.getIp(),
                LocalDateTime.parse(hitDtoRequest.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
