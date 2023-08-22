package ru.practicum.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import ru.practicum.event.model.Event;

import java.util.HashSet;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
public class UpdateCompilationRequest {
    private final Boolean pinned;
    @Length(min = 1, max = 50, message = "Название не может быть короче 1 символа или длинее 50")
    private final String title;
    private final HashSet<Long> events;
}
