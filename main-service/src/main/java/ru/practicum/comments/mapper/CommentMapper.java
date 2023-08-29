package ru.practicum.comments.mapper;

import ru.practicum.comments.dto.CommentResponseDto;
import ru.practicum.comments.dto.NewCommentDto;
import ru.practicum.comments.model.Comment;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

public class CommentMapper {
    public static Comment toDto(NewCommentDto newCommentDto, User user, Event event) {
        return Comment
                .builder()
                .id(null)
                .author(user)
                .text(newCommentDto.getText())
                .event(event)
                .createdOn(LocalDateTime.now())
                .build();
    }

    public static CommentResponseDto toDto(Comment comment) {
        return CommentResponseDto
                .builder()
                .author(comment.getAuthor().getId())
                .text(comment.getText())
                .createdOn(comment.getCreatedOn())
                .id(comment.getId())
                .event(comment.getEvent().getId())
                .build();
    }
}
