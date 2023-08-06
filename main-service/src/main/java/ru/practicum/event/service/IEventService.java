package ru.practicum.event.service;

import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.Event;

import java.util.List;

public interface IEventService {
    List<EventFullDto> getEventByUserId(Long id, int from, int size);
    NewEventDto createNewEvent(Long userId, NewEventDto newEventDto);
    EventFullDto getFullInfoByUserIdAndEventId(Long userId, Long eventId);
    NewEventDto updateEventCurrentUser(Long userId, Long eventId, NewEventDto newEventDto);
    NewEventDto getInfoEventByCurrentUser(Long userId, Long eventId);
    NewEventDto updateStatusEvent(Long userId, Long eventId);
}
