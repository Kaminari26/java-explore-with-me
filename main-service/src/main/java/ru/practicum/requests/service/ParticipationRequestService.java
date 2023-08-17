package ru.practicum.requests.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.event.RequestStatus;
import ru.practicum.event.State;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exeption.DataConflictException;
import ru.practicum.exeption.UserNotFoundException;
import ru.practicum.requests.dto.EventParticipationRequestStatusUpdateRequestDto;
import ru.practicum.requests.dto.EventParticipationRequestStatusUpdateResponseDto;
import ru.practicum.requests.model.ParticipationRequest;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.mapper.ParticipationRequestMapper;
import ru.practicum.requests.repository.ParticipationRequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipationRequestService implements IParticipationRequestService{
    private final ParticipationRequestRepository participationRequestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public ParticipationRequestService(ParticipationRequestRepository participationRequestRepository,UserRepository userRepository,EventRepository eventRepository) {
        this.participationRequestRepository = participationRequestRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public ParticipationRequestDto createParticipationRequest(Long userId, Long eventId, LocalDateTime localDateTime) {

        User requester = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new UserNotFoundException("Событие не найдено"));
        if (event.getState() != State.PUBLISHED) {
            throw new DataConflictException("Событие еще не опубликовано");
        }
        int limit = event.getParticipantLimit();
        if (limit != 0) {
            Long numberOfRequests = participationRequestRepository.countByEventIdAndStatus(eventId, RequestStatus.CONFIRMED);
            if (numberOfRequests >= limit) {
                throw new DataConflictException("Слишком много участников события");
            }
        }
        ParticipationRequest participationRequest = ParticipationRequest.builder().
                created(localDateTime).
                requester(requester).
                event(event).
                status(RequestStatus.PENDING).
                build();
        return ParticipationRequestMapper.toParticipationRequestDto(participationRequestRepository.save(participationRequest));
    }

    @Override
    public ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId) {
        ParticipationRequest participationRequest = participationRequestRepository.findById(requestId).orElseThrow(() -> new UserNotFoundException("Запрос не найден"));
        if (participationRequest.getRequester().getId().equals(userId)) {
            participationRequest.setStatus(RequestStatus.CANCELED);
            return ParticipationRequestMapper.toParticipationRequestDto(participationRequestRepository.save(participationRequest));
        }
        throw new DataConflictException("Чужой запрос нельзя отменить");

    }

    @Override
    public List<ParticipationRequestDto> getParticipationRequest(Long userId) {
        List<ParticipationRequest> participationRequests = participationRequestRepository.findAllByRequesterId(userId);
        return participationRequests.stream().map(ParticipationRequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getAllUserEventRequests(Long eventId, Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new UserNotFoundException("Событие не найдено")
        );
        if (!event.getInitiator().getId().equals(userId)) {
            throw new DataConflictException("Пользователь не может просматривать запросы к событию, автором которого он не является");
        }
        List<ParticipationRequest> requests = participationRequestRepository.findAllByEventId(eventId);
        return requests.stream()
                .map(ParticipationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public EventParticipationRequestStatusUpdateResponseDto updateParticipationRequestsStatus(
            EventParticipationRequestStatusUpdateRequestDto updater,
            Long eventId,
            Long userId
    ) {
        RequestStatus status = updater.getStatus();
        if (status == RequestStatus.CONFIRMED || status == RequestStatus.REJECTED) {
            if (!userRepository.existsById(userId)) {
                throw new UserNotFoundException("Пользователь не найден");
            }
            Event event = eventRepository.findById(eventId).orElseThrow(
                    () -> new UserNotFoundException("Событие не найдено")
            );
            if (!event.getInitiator().getId().equals(userId)) {
                throw new DataConflictException("Пользователь не может обновлять запросы к событию, автором которого он не является");
            }
            Integer participantLimit = event.getParticipantLimit();
            if (!event.getRequestModeration() || participantLimit == 0) {
                throw new DataConflictException("Событию не нужна модерация");
            }
            Long numberOfParticipants = participationRequestRepository.countByEventIdAndStatus(eventId, RequestStatus.CONFIRMED);
            if (numberOfParticipants >= participantLimit) {
                throw new DataConflictException("В событии уже максимальное кол-во участников");
            }
            List<ParticipationRequest> requests = participationRequestRepository.findAllByIdIn(updater.getRequestIds());
            RequestStatus newStatus = updater.getStatus();
            for (ParticipationRequest request : requests) {
                if (request.getEvent().getId().equals(eventId)) {
                    if (participantLimit > numberOfParticipants) {
                        if (newStatus == RequestStatus.CONFIRMED && request.getStatus() != RequestStatus.CONFIRMED) {
                            numberOfParticipants++;
                        }
                        request.setStatus(newStatus);
                    } else {
                        request.setStatus(RequestStatus.REJECTED);
                    }
                } else {
                    throw new DataConflictException("Запрос и событие не совпадают");
                }
            }
            requests = participationRequestRepository.saveAll(requests);
            List<ParticipationRequest> confirmedRequests = participationRequestRepository.findAllByEventIdAndStatus(eventId, RequestStatus.CONFIRMED);
            List<ParticipationRequest> rejectedRequests = participationRequestRepository.findAllByEventIdAndStatus(eventId, RequestStatus.REJECTED);

            List<ParticipationRequestDto> confirmedRequestDtos = confirmedRequests.stream()
                    .map(ParticipationRequestMapper::toDto)
                    .collect(Collectors.toList());

            List<ParticipationRequestDto> rejectedRequestDtos = rejectedRequests.stream()
                    .map(ParticipationRequestMapper::toDto)
                    .collect(Collectors.toList());
            return new EventParticipationRequestStatusUpdateResponseDto(confirmedRequestDtos, rejectedRequestDtos);
        } else {
            throw new IllegalArgumentException("Доступны только статусы CONFIRMED или REJECTED");
        }
    }

}
