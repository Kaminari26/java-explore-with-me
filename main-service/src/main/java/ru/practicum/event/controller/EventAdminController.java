package ru.practicum.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.model.Category;
import ru.practicum.event.State;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.UpdateEventAdminRequest;
import ru.practicum.event.service.IEventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/admin/events")
public class EventAdminController {
    private final IEventService eventService;

    @Autowired
    public EventAdminController(IEventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventFullDto> getEvent(@RequestParam List<Long> users,
                                       @RequestParam List<State> states,
                                       @RequestParam List<Long> categories,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                       @RequestParam(value = "from", defaultValue = "0", required = false) Integer from,
                                       @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {

        log.info("Пришел запрос Get /admin/events users: {},states: {},categories: {},rangeStart: {}, rangeEnd: {}, from: {}, size: {}",
                users,states,categories,rangeStart,rangeEnd, from, size);

        List<EventFullDto>  eventFullDtos = eventService.getFullEventInfoByParam(users,states,categories,rangeStart,rangeEnd,from,size);
        log.info("Отправлен ответ: {}", eventFullDtos);
        return eventFullDtos;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventState(@PathVariable Long eventId, @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("Пришел запрос Patch eventId {}, updateEventAdminRequest {}" , eventId,updateEventAdminRequest);
        EventFullDto eventFullDto = eventService.updateEvent(eventId,updateEventAdminRequest);
        log.info("Отправлен ответ: {} , {}" , eventFullDto,eventFullDto.getState());
        return eventFullDto;
    }

}
