package ru.practicum.event.controller;

import com.sun.nio.sctp.IllegalReceiveException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.service.IEventService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users/{userId}")
public class EventPrivateController {
    private final IEventService eventService;

    @Autowired
    public EventPrivateController(IEventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public List<EventFullDto> getEventsCurrentUser(@PathVariable Long userId,
                                                   @RequestParam(value = "from", defaultValue = "0", required = false) Integer from,
                                                   @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
        log.info("Пришел запрос Get /users/{userId}/events userId: {}, from: {}, size: {}", userId, from, size);
        if (size <= 0 || from < 0) {
            throw new IllegalReceiveException("Неверно указан параметр");
        }
        List<EventFullDto> eventFullDtos = eventService.getEventByUserId(userId,from,size);
        log.info("Отправлен ответ: {}", eventFullDtos);
        return eventFullDtos;
    }
    @PostMapping("/events")
    public NewEventDto createNewEvent(@PathVariable Long userId,@RequestBody NewEventDto newEventDto) {
        log.info("Пришел запрос Post /users/{userId}/events userId: {}, newEventDto {}", userId, newEventDto);
        NewEventDto event = eventService.createNewEvent(userId, newEventDto);
        log.info("Отправлен ответ: {}", event);
        return event;
    }
}
