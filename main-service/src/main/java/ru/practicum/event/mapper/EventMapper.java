package ru.practicum.event.mapper;

import ru.practicum.category.dto.mapper.CategoryMapper;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.model.Event;
import ru.practicum.user.mapper.UserMapper;

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
}