package ru.practicum.compilation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.UtilityClass;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationsRepository;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exeption.UserNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompilationsService implements ICompilationsService {
    private final CompilationsRepository compilationsRepository;
    private final EventRepository eventRepository;
    private final UtilityClass utilityClass;

    @Autowired
    public CompilationsService(CompilationsRepository compilationsRepository, EventRepository eventRepository, UtilityClass utilityClass) {
        this.compilationsRepository = compilationsRepository;
        this.eventRepository = eventRepository;
        this.utilityClass = utilityClass;
    }

    @Override
    @Transactional
    public CompilationDto createNewCompilation(NewCompilationDto newCompilationDto) {
        Set<Long> eventsIds = newCompilationDto.getEvents();
        Compilation compilation = CompilationMapper.toDto(newCompilationDto);
        if (eventsIds != null) {
            List<Event> events = eventRepository.findAllById(eventsIds);
            compilation.setEvents(new HashSet<>(events));
        }
        Compilation savedCompilation = compilationsRepository.save(compilation);
        if (savedCompilation.getEvents() == null) {
            return CompilationMapper.toDto(savedCompilation, null);
        }
        List<EventShortDto> eventsDto = utilityClass.makeEventShortDto(savedCompilation.getEvents());
        return CompilationMapper.toDto(savedCompilation, eventsDto);
    }


    @Transactional
    @Override
    public void removeCompilation(Long id) {
        if (!compilationsRepository.existsById(id)) {
            throw new UserNotFoundException("Подборка не найдена");
        }
        compilationsRepository.deleteById(id);

    }

    @Transactional(readOnly = true)
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = compilationsRepository.findById(compId).orElseThrow(
                () -> new UserNotFoundException("Подборка не найдена")
        );
        List<EventShortDto> eventsDto = utilityClass.makeEventShortDto(compilation.getEvents());
        return CompilationMapper.toDto(compilation, eventsDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> getCompilationsByPinned(Boolean pinned, Integer from, Integer size) {
        List<Compilation> compilations;
        PageRequest pageRequest = PageRequest.of(from / size, size);
        if (pinned != null) {
            compilations = compilationsRepository.findAllByPinned(pinned, pageRequest);
        } else {
            compilations = compilationsRepository.findAll(pageRequest).getContent();
        }

        if (compilations.isEmpty()) {
            return new ArrayList<>();
        }
        Set<Event> events = compilations.stream()
                .flatMap(compilation -> compilation.getEvents().stream())
                .collect(Collectors.toSet());
        List<EventShortDto> eventsDtoList = utilityClass.makeEventShortDto(events);

        Map<Long, EventShortDto> eventDtosMap = new HashMap<>();
        for (EventShortDto eventShortDto : eventsDtoList) {
            eventDtosMap.put(
                    eventShortDto.getId(),
                    eventShortDto
            );
        }

        Map<Long, List<EventShortDto>> eventsDtoMapByCompilationId = compilations.stream()
                .collect(Collectors.toMap(Compilation::getId, compilation -> {
                    Set<Event> eventsSet = compilation.getEvents();
                    return eventsSet.stream()
                            .map(event -> eventDtosMap.get(event.getId()))
                            .collect(Collectors.toList());
                }));

        return compilations.stream()
                .map(compilation -> {
                    Long compilationId = compilation.getId();
                    List<EventShortDto> eventShortDtos = eventsDtoMapByCompilationId.get(compilationId);
                    return CompilationMapper.toDto(compilation, eventShortDtos);
                }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = compilationsRepository.findById(compId).orElseThrow(
                () -> new UserNotFoundException("Подборка не найдена")
        );

        Set<Long> eventsIds = updateCompilationRequest.getEvents();
        if (eventsIds != null) {
            List<Event> events = eventRepository.findAllById(eventsIds);
            compilation.setEvents(new HashSet<>(events));
        }
        if (updateCompilationRequest.getEvents() != null) {
            Set<Event> events = new HashSet<>(eventRepository.findAllById(updateCompilationRequest.getEvents()));
            CompilationMapper.toDto(updateCompilationRequest, compilation, events);
        }

        CompilationMapper.toDto(updateCompilationRequest, compilation, null);

        List<EventShortDto> eventsDto = utilityClass.makeEventShortDto(compilation.getEvents());

        Set<Event> updatedEvents = compilationsRepository.save(compilation).getEvents();

        return CompilationMapper.toDto(compilation, eventsDto);
    }
}
