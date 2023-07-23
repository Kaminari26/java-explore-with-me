package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Hit {
    private Long id;

    private String app;
    private String uri;
    private String ip;
    private LocalDateTime time;
}
