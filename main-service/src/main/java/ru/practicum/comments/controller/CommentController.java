package ru.practicum.comments.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.dto.CommentResponseDto;
import ru.practicum.comments.dto.NewCommentDto;
import ru.practicum.comments.service.ICommentService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/comments")
public class CommentController {
    private final ICommentService commentService;

    @Autowired
    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto addComment(@PathVariable Long userId,
                                         @RequestParam Long eventId,
                                         @Valid @RequestBody NewCommentDto commentDto) {
        log.info("Пришел запрос Post /users/{userId}/comments userId: {}, eventId: {}, commentDto: {}.",userId,eventId,commentDto);
        CommentResponseDto commentResponseDto = commentService.addComment(userId, eventId, commentDto);
        log.info("Отправлен ответ:{}.", commentResponseDto);
        return commentResponseDto;
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(@PathVariable Long userId,
                                  @PathVariable Long commentId) {
        log.info("Пришел запрос Delete /users/{userId}/comments/{commentId} userId: {}, commentId: {}.",userId,commentId);
        commentService.deleteCommentById(commentId, userId);
        log.info("Коммент удален");
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto updateComment(@PathVariable Long userId,
                                            @PathVariable Long commentId,
                                            @Valid @RequestBody NewCommentDto commentDto) {
        log.info("Пришел запрос Patch /users/{userId}/comments/{commentId} userId: {}, commentId: {}, commentDto: {}.",userId,commentId,commentDto);
        CommentResponseDto commentResponseDto = commentService.updateComment(commentId, userId, commentDto);
        log.info("Отправлен ответ:{}.", commentResponseDto);
        return commentResponseDto;
    }
}
