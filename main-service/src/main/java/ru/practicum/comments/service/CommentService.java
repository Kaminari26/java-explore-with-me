package ru.practicum.comments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comments.dto.CommentResponseDto;
import ru.practicum.comments.dto.NewCommentDto;
import ru.practicum.comments.mapper.CommentMapper;
import ru.practicum.comments.model.Comment;
import ru.practicum.comments.repository.CommentRepository;
import ru.practicum.event.RequestStatus;
import ru.practicum.event.State;
import org.springframework.util.StringUtils;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exeption.DataConflictException;
import ru.practicum.exeption.UserNotFoundException;
import ru.practicum.requests.model.ParticipationRequest;
import ru.practicum.requests.repository.ParticipationRequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;
    private final ParticipationRequestRepository participationRequest;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, EventRepository eventRepository, ParticipationRequestRepository participationRequest) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.participationRequest = participationRequest;
    }

    @Override
    @Transactional
    public CommentResponseDto addComment(Long userId, Long eventId, NewCommentDto commentDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new UserNotFoundException("Событие не найдено"));


        if (event.getState() != State.PUBLISHED) {
            throw new DataConflictException("Событие не опубликовано");
        }

        if (!Objects.equals(user.getId(), event.getInitiator().getId())) {
            List<ParticipationRequest> requests = participationRequest.findAllByEventIdAndStatusAndRequesterId(eventId, RequestStatus.CONFIRMED, userId);
            if (requests.isEmpty()) {
                throw new DataConflictException("Нужно быть участником или организатором");
            }
        }
        Optional<Comment> foundComment = commentRepository.findByEventIdAndAuthorId(eventId, userId);
        if (foundComment.isPresent()) {
            throw new DataConflictException("Можно оставить только один комментарий");
        }
        return CommentMapper.toDto(commentRepository.save(CommentMapper.toDto(commentDto, user, event)));
    }

    @Override
    @Transactional
    public void deleteCommentById(Long commentId, Long userId) {
        Comment comment = checkIfCommentExist(commentId);
        checkIfUserIsTheAuthor(comment.getAuthor().getId(), userId);
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public void deleteCommentByAdmin(Long commentId) {
        checkIfCommentExist(commentId);
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(Long commentId, Long userId, NewCommentDto commentDto) {
        Comment comment = checkIfCommentExist(commentId);

        checkIfUserIsTheAuthor(comment.getAuthor().getId(), userId);

        String newText = commentDto.getText();
        if (StringUtils.hasLength(newText)) {
            comment.setText(newText);
        }
        return CommentMapper.toDto(comment);
    }

    @Transactional()
    public List<CommentResponseDto> getAllCommentsByEventId(Long eventId, Integer from, Integer size) {
        eventRepository.findById(eventId).orElseThrow(() -> new UserNotFoundException("Событие не найдено")
        );

        PageRequest pageRequest = PageRequest.of(from, size);
        List<Comment> comments = commentRepository.findAllByEventIdOrderByCreatedOnDesc(eventId, pageRequest);

        return comments.stream().map(CommentMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getLast10CommentsByEventId(Long eventId) {
        eventRepository.findById(eventId).orElseThrow(() -> new UserNotFoundException("Событие не найдено")
        );
        List<Comment> comments = commentRepository.findTop10ByEventIdOrderByCreatedOnDesc(eventId);
        return comments.stream().map(CommentMapper::toDto).collect(Collectors.toList());
    }

    private void checkIfUserIsTheAuthor(Long authorId, Long userId) {
        if (!Objects.equals(authorId, userId)) {
            throw new DataConflictException("Не автор");
        }
    }

    private Comment checkIfCommentExist(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new UserNotFoundException("Комментарий не найден"));

    }
}
