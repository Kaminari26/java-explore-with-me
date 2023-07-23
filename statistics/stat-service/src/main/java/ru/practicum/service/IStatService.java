package ru.practicum.service;

import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.model.Hit;
import ru.practicum.model.HitDto;

import java.sql.Timestamp;
import java.util.List;

public interface IStatService {
    void addHit(Hit hit);
    List<HitDto> getHit(Timestamp start, Timestamp end, String uris, Boolean unique);

}
