package ru.practicum.comments.service;

import ru.practicum.comments.dto.CommentResponseDto;
import ru.practicum.comments.dto.NewCommentDto;

public interface ICommentService {
    CommentResponseDto addComment(Long userId, Long eventId, NewCommentDto commentDto);

    void deleteCommentById(Long commentId, Long userId);

    void deleteCommentByAdmin(Long commentId);

    CommentResponseDto updateComment(Long commentId, Long userId, NewCommentDto newCommentDto);
}
