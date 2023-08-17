package ru.practicum.requests.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.service.IParticipationRequestService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users/{userId}/requests")
public class EventPrivateRequestsController {
    private final IParticipationRequestService participationRequestService;
    private EventPrivateRequestsController(IParticipationRequestService participationRequestService) {
        this.participationRequestService = participationRequestService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ParticipationRequestDto createNewRequest(@PathVariable Long userId , @RequestParam Long eventId) {
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("Пришел запрос Post /users/{userId}/requests userId: {}, eventId{}.", userId,eventId);
        ParticipationRequestDto participationRequestDtoReady = participationRequestService.createParticipationRequest(userId, eventId,localDateTime);
        log.info("Отправлен ответ: {}", participationRequestDtoReady);
        return participationRequestDtoReady;
    }

    @GetMapping
    public List<ParticipationRequestDto> getParticipationRequestByUserId(@PathVariable Long userId) {
        log.info("Пришел запрос Get /users/{userId}/requests userId: {}.", userId);
        List<ParticipationRequestDto> participationRequestDtoReady = participationRequestService.getParticipationRequest(userId);
        log.info("Отправлен ответ: {}", participationRequestDtoReady);
        return participationRequestDtoReady;
    }
    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelParticipationRequest(@PathVariable Long userId ,@PathVariable Long requestId) {
        log.info("Пришел запрос /users/{userId}/requests/{requestId}/cancel userId: {}, requestId {}.", userId, requestId);
        ParticipationRequestDto requestDto = participationRequestService.cancelParticipationRequest(userId, requestId);
        log.info("Отправлен ответ: {}", requestDto);
        return requestDto;
    }
}
