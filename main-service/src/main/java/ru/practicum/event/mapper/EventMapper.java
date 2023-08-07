package ru.practicum.event.mapper;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.event.Location;
import ru.practicum.event.State;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.Event;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

public class EventMapper {
    public static EventFullDto toDto (Event event, int confirmedRequests,int views) {
        return EventFullDto.builder().
                annotation(event.getAnnotation()).
                category(CategoryMapper.toDtoCategory(event.getCategory())).
                confirmedRequests(confirmedRequests).
                createdOn(event.getCreatedOn()).
                description(event.getDescription()).
                id(event.getId()).
                eventDate(event.getEventDate()).
                initiator(UserMapper.toDtoShortUser(event.getInitiator())).
                location(event.getLocation()).
                paid(event.getPaid()).
                participantLimit(event.getParticipantLimit()).
                publishedOn(event.getPublishedOn()).
                requestModeration(event.getRequestModeration()).
                state(event.getState()).
                title(event.getTitle()).
                views(views).
                build();
    }
    public static Event toDto (NewEventDto newEventDto, User initiator, Category category, State state) {
        return Event.builder().
                id(null).
                eventDate(newEventDto.getEventDate().toString()).
                annotation(newEventDto.getAnnotation()).
                category(category).
                createdOn(LocalDateTime.now().toString()).
                initiator(initiator).
                description(newEventDto.getDescription()).
                paid(newEventDto.getPaid()).
                participantLimit(newEventDto.getParticipantLimit()).
                state(state.toString()).
                location(newEventDto.getLocation()).
                publishedOn(null).
                requestModeration(newEventDto.getRequestModeration()).
                title(newEventDto.getTitle()).
                build();
    }

    public static EventFullDto toDto(Event event, Integer confirmedRequests, Integer views) {
        return EventFullDto.builder().
                annotation(event.getAnnotation()).
                category(CategoryMapper.toDtoCategory(event.getCategory())).
                confirmedRequests(confirmedRequests).
                createdOn(event.getCreatedOn()).
                description(event.getDescription()).
                eventDate(event.getEventDate()).
                id(event.getId()).
                initiator(UserMapper.toDtoShortUser(event.getInitiator())).
                location(event.getLocation()).
                paid(event.getPaid()).
                participantLimit(event.getParticipantLimit()).
                publishedOn(event.getPublishedOn()).
                requestModeration(event.getRequestModeration()).
                state(event.getState()).title(event.getTitle()).views(views).
                build();
    }
}