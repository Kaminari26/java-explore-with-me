package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class HitDto {
    private String app;
    private String uri;
    private Long hits;
}
