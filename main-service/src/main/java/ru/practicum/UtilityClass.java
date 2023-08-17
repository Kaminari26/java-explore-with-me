package ru.practicum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatsClient;
import ru.practicum.event.RequestStatus;
import ru.practicum.event.dto.ConfirmedEventDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.model.HitDto;
import ru.practicum.requests.repository.ParticipationRequestRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UtilityClass {
    private final ParticipationRequestRepository requestRepository;
    private static final StatsClient statClient = new StatsClient();

    @Autowired
    public UtilityClass(ParticipationRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    private static final String START = "1970-01-01 00:00:00";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    protected static final String USER_NOT_FOUND = "Пользователь не найден";
    protected static final String CATEGORY_NOT_FOUND = "Категория не найдена";
    protected static final String EVENT_NOT_FOUND = "Событие не найдено";
    protected static final String COMPILATION_NOT_FOUND = "Компиляция не найдена";
    protected static final String REQUEST_NOT_FOUND = "Запрос не найден";

    public List<EventShortDto> makeEventShortDto(Collection<Event> events) {
        Map<String, Long> viewStatsMap = toViewStats(events);

        Map<Long, Long> confirmedRequests = getConfirmedRequests(events);

        List<EventShortDto> eventsDto = new ArrayList<>();
        for (Event event : events) {
            Long eventId = event.getId();
            Long reqCount = confirmedRequests.get(eventId);
            Long views = viewStatsMap.get(String.format("/events/%s", eventId));
            if (reqCount == null) {
                reqCount = 0L;
            }
            if (views == null) {
                views = 0L;
            }
            eventsDto.add(
                    EventMapper.toDtoShort(event, reqCount, views)
            );
        }

        return eventsDto;
    }

    public Map<String, Long> toViewStats(Collection<Event> events) {
        List<String> urisToSend = new ArrayList<>();
        for (Event event : events) {
            urisToSend.add(String.format("/events/%s", event.getId()));
        }

        List<HitDto> viewStats = statClient.getStats(
                START,
                LocalDateTime.now().format(formatter),
                urisToSend,
                true
        );

        return viewStats.stream()
                .collect(Collectors.toMap(HitDto::getUri, HitDto::getHits));
    }

    public Map<Long, Long> getConfirmedRequests(Collection<Event> events) {
        List<Long> eventsIds = events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());
        List<ConfirmedEventDto> confirmedDtos =
                requestRepository.countConfirmedRequests(eventsIds, RequestStatus.CONFIRMED);
        return confirmedDtos.stream()
                .collect(Collectors.toMap(ConfirmedEventDto::getEventId, ConfirmedEventDto::getCount));
    }

    public static String formatTimeToString(LocalDateTime time) {
        return time.format(formatter);
    }
}