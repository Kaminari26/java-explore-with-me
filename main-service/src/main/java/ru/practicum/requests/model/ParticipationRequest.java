package ru.practicum.requests.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.event.RequestStatus;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @NotNull
    private LocalDateTime created;
    @ManyToOne
    private Event event;
    @ManyToOne
    @NotNull
    private User requester;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private RequestStatus status;
}
