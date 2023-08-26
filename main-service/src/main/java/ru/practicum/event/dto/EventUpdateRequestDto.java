package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.event.LocationDto;
import ru.practicum.event.StateAction;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@ToString
@Builder
public class EventUpdateRequestDto {
    private  String annotation;
    private  Long category;
    private  String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;

    private  Long initiator;
    private  LocationDto location;
    private  Boolean paid;
    private  Integer participantLimit;
    private  Boolean requestModeration;
    private  StateAction stateAction;
    private  String title;
}
