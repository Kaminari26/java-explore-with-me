package ru.practicum.event.service;

import ru.practicum.event.dto.EventDto;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.model.Event;

import java.util.List;

public interface IEventService {
    List<EventFullDto> getEventByUserId(Long id, int from, int size);
}
