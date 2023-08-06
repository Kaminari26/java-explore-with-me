package ru.practicum.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.event.model.Event;

import javax.persistence.Entity;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CompilationDto {
    private List<Event> events;
    private Long id;
    private Boolean pinned;
    private String title;

}
