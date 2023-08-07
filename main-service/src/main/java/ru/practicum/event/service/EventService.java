package ru.practicum.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.State;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exeption.UserNotFoundException;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.user.service.IUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class EventService implements IEventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public EventService(EventRepository eventRepository,UserRepository userRepository,CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
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
    @Transactional(readOnly = true)
    public EventFullDto createNewEvent(Long userId, NewEventDto newEventDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(() -> new UserNotFoundException("Категория не найдена"));
        Event event = EventMapper.toDto(newEventDto, user, category, State.PENDING);
        eventRepository.save(event);
        return EventMapper.toDto(event, 0, 0);
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
