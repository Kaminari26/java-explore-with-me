package ru.practicum.compilation.dto;

import ru.practicum.event.model.Event;

import java.util.List;

public class UpdateCompilationRequest {
    private List<Event> events;
    private Boolean pinned;
    private String title;
}
