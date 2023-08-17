package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import ru.practicum.event.LocationDto;
import ru.practicum.event.StateAction;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@ToString
@Builder
public class EventUpdateRequestDto {
    @Length( min = 20, max = 2000)
    private final String annotation;
    private final Long category;
    @Length( min = 20, max = 7000)
    private final String description;
    @Future(message = "Неверно указана дата")
    private final LocalDateTime eventDate;
    private final Long initiator;
    private final LocationDto location;
    private final Boolean paid;
    private final Integer participantLimit;
    private final Boolean requestModeration;
    private final StateAction stateAction;
    @Length( min = 3, max = 120)
    private final String title;
}
