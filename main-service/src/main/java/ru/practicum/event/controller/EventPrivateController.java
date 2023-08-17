package ru.practicum.event.controller;

import com.sun.nio.sctp.IllegalReceiveException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventUpdateRequestDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.service.IEventService;
import ru.practicum.requests.dto.EventParticipationRequestStatusUpdateRequestDto;
import ru.practicum.requests.dto.EventParticipationRequestStatusUpdateResponseDto;
import ru.practicum.requests.service.IParticipationRequestService;
import ru.practicum.requests.dto.ParticipationRequestDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users/{userId}")
public class EventPrivateController {
    private final IEventService eventService;
    private final IParticipationRequestService participationRequestService;

    @Autowired
    public EventPrivateController(IEventService eventService, IParticipationRequestService participationRequestService) {
        this.eventService = eventService;
        this.participationRequestService = participationRequestService;
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
    @ResponseStatus(value = HttpStatus.CREATED)
    public EventFullDto createNewEvent(@PathVariable Long userId,@RequestBody NewEventDto newEventDto) {
        log.info("Пришел запрос Post /users/{userId}/events userId: {}, newEventDto {}", userId, newEventDto);
        EventFullDto event = eventService.createNewEvent(userId, newEventDto);
        log.info("Отправлен ответ: {}", event);
        return event;
    }
    @GetMapping("/events/{eventId}")
    public EventFullDto getEventByIdByInitiator(@PathVariable("userId") Long userId, @PathVariable("eventId") Long eventId) {
        log.info("Пришел запрос /users/{userId}/events/{eventId}  userId: {}, с eventId: {}", userId, eventId);
      return eventService.getEventByIdByInitiator(eventId, userId);

    }
    @GetMapping("/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getAllUserEventRequests(@PathVariable("userId") Long userId, @PathVariable("eventId") Long eventId) {
        log.info("Вызов всех запросов с eventId: {} с userId: {}", eventId, userId);
        return participationRequestService.getAllUserEventRequests(eventId, userId);
    }

    @PatchMapping("/events/{eventId}")
    public EventFullDto updateEventByUser(@PathVariable("userId") Long userId, @PathVariable("eventId") Long eventId,
                                          @Valid @RequestBody EventUpdateRequestDto eventDto
    ) {
        log.info("Пришел запрос Patch /users/{userId}/events/{eventId}  с userId: {}, eventId: {}", userId, eventId);
        EventFullDto eventFullDto = eventService.updateEventByUser(eventDto, eventId, userId);
        log.info("Отправлен ответ: {}", eventFullDto);
        return eventFullDto;
    }

    @PatchMapping("/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventParticipationRequestStatusUpdateResponseDto updateRequestsStatus(
            @Valid @RequestBody EventParticipationRequestStatusUpdateRequestDto updater,
            @PathVariable("userId") Long userId,
            @PathVariable("eventId") Long eventId
    ) {
        log.info("Пришел запрос Patch /users/{userId}/{userId}/events/{eventId}/requests eventId: {}, для userId: {}", eventId, userId);
        EventParticipationRequestStatusUpdateResponseDto eventParticipationRequestStatusUpdateResponseDto = participationRequestService.updateParticipationRequestsStatus(updater, eventId, userId);
        log.info("Отправлен ответ: {}", eventParticipationRequestStatusUpdateResponseDto) ;
        return eventParticipationRequestStatusUpdateResponseDto;
    }
}
