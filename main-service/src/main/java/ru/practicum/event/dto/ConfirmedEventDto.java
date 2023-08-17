package ru.practicum.event.dto;

import lombok.*;

@AllArgsConstructor
@Data
@ToString
@Builder
public class ConfirmedEventDto {
    private Long eventId;
    private Long count;
}
