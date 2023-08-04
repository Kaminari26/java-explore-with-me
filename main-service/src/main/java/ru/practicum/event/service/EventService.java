package ru.practicum.event.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

public class EventService implements IEventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<EventFullDto> getEventByUserId(Long id, int from, int size) {
        int page = 0;
        if (from != 0) {
            page = from / size;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("start").descending());
        List<EventFullDto> eventFullDtos = eventRepository.findAllByInitiatorId(id,pageable);

        return null;
    }
}
