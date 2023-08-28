package ru.practicum.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.category.model.Category;
import ru.practicum.event.Location;
import ru.practicum.event.State;
import ru.practicum.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Event {
    @Size(max = 2000, min = 20)
    @NotBlank
    private String annotation;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @Size(max = 7000, min = 20)
    //@NotBlank(message = "Необходимо описание")
    private String description;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "initiator_id", referencedColumnName = "id")
    @NotNull
    private User initiator;
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    @Column(name = "state")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private State state;
    @NotBlank
    @Size(max = 120, min = 3)
    private String title;

}
