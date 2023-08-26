package ru.practicum.requests.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.event.RequestStatus;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipationRequestDto {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime created;

    private Long event;
    private Long Id;
    private Long requester;
    private RequestStatus status;
}
