package ru.practicum.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService implements IEventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<EventFullDto> getEventByUserId(Long id, int from, int size) {
        int page = 0;
        if (from != 0) {
            page = from / size;
        }
        Pageable pageable = PageRequest.of(page, size);
        List<Event> events = eventRepository.findAllByInitiatorId(id,pageable);
        List<EventFullDto> eventFullDtos = new ArrayList<>();
        for(Event event: events){
            eventFullDtos.add(EventMapper.toDto(event,0,0));
        }
        return eventFullDtos;
    }

    @Override
    public NewEventDto createNewEvent(Long userId, NewEventDto newEventDto) {
        return null;
    }

    @Override
    public EventFullDto getFullInfoByUserIdAndEventId(Long userId, Long eventId) {
        return null;
    }

    @Override
    public NewEventDto updateEventCurrentUser(Long userId, Long eventId, NewEventDto newEventDto) {
        return null;
    }

    @Override
    public NewEventDto getInfoEventByCurrentUser(Long userId, Long eventId) {
        return null;
    }

    @Override
    public NewEventDto updateStatusEvent(Long userId, Long eventId) {
        return null;
    }
}
