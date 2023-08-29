package ru.practicum.comments.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewCommentDto {
    @NotBlank
    @Size(min = 10, max = 2000)
    private String text;

}
