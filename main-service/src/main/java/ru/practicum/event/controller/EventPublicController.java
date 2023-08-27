package ru.practicum.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.service.IEventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/events")
public class EventPublicController {
    private final IEventService eventService;

    @Autowired
    public EventPublicController(IEventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventShortDto> getEvents(
            HttpServletRequest request,
            @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(name = "size", defaultValue = "10") @Positive Integer size,
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(required = false) Boolean onlyAvailable,
            @RequestParam(required = false) String sort
    ) {
        log.info("Пришел запрос Get /events from: {}, size: {}, text: {}, categories: {}, paid: {}, rangeStart: {}, rangeEnd: {}, onlyAvailable: {}, sort: {}.",
                from, size, text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort);

        List<EventShortDto> eventShortDtos = eventService.getEventsPublicController(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort,
                request.getRemoteAddr(), request.getRequestURI(), from, size);
        log.info("Отправлен ответ {}", eventShortDtos);
        return eventShortDtos;
    }

    @GetMapping(path = "/{eventId}")
    public EventFullDto getEvent(@PathVariable("eventId") Long eventId, HttpServletRequest request) {
        log.info("Пришел запрос Get /events/{eventId} eventId: {}", eventId);
        return eventService.getEventByIdPublic(eventId, request.getRemoteAddr(), request.getRequestURI());
    }
}
