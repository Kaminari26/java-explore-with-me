package ru.practicum.mapper;

import org.springframework.stereotype.Service;
import ru.practicum.model.Hit;
import ru.practicum.model.HitDto;

@Service
public class HitMapper {
    public static HitDto toHitDto(Hit hit) {
        return new HitDto(hit.getApp(),
                hit.getUri(), null);
    }
}
