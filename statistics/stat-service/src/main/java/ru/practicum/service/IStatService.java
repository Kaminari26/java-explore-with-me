package ru.practicum.service;

import ru.practicum.model.HitDto;
import ru.practicum.model.HitDtoRequest;

import java.sql.Timestamp;
import java.util.List;

public interface IStatService {
    void addHit(HitDtoRequest hitDtoRequest);

    List<HitDto> getHit(Timestamp start, Timestamp end, List<String> uris, Boolean unique);

}
