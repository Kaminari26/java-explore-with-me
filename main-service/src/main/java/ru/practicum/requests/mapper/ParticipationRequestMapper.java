package ru.practicum.requests.mapper;

import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.model.ParticipationRequest;

public class ParticipationRequestMapper {
    public static ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest) {
        return ParticipationRequestDto
                .builder()
                .requester(participationRequest.getRequester().getId())
                .id(participationRequest.getId())
                .created(participationRequest.getCreated())
                .event(participationRequest.getEvent().getId())
                .status(participationRequest.getStatus())
                .build();
    }

    public static ParticipationRequestDto toDto(ParticipationRequest participationRequest) {
        return ParticipationRequestDto
                .builder()
                .requester(participationRequest.getRequester().getId())
                .created(participationRequest.getCreated())
                .event(participationRequest.getEvent().getId())
                .id(participationRequest.getId())
                .status(participationRequest.getStatus())
                .build();
    }
}
