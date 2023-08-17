package ru.practicum.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;
import ru.practicum.event.Location;
import ru.practicum.event.State;
import ru.practicum.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Event {
    @Size(max = 2000)
    private String annotation;
    @ManyToOne
    private Category category;
    private LocalDateTime  createdOn;
    @Size(max = 7000)
    private String description;

    private LocalDateTime  eventDate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "initiator_id", referencedColumnName = "id")
    private User initiator;
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade =CascadeType.ALL )
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private LocalDateTime  publishedOn;
    private Boolean requestModeration;
    @Column(name = "state")
    @Enumerated(value = EnumType.STRING)
    private State state;
    private String title;

}
