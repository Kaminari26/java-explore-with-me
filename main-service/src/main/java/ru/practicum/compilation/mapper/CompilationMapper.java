package ru.practicum.compilation.mapper;

import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.Event;

import java.util.List;
import java.util.Set;

public class CompilationMapper {
    public static Compilation toDto(NewCompilationDto newCompilationDto) {
        return Compilation.builder().
                id(null).title(newCompilationDto.getTitle()).
                events(null).
                pinned(newCompilationDto.isPinned()).
                build();
    }

    public static CompilationDto toDto(Compilation compilation, List<EventShortDto> events) {
        return CompilationDto.builder().
                id(compilation.getId()).title(compilation.getTitle()).
                events(events).
                pinned(compilation.getPinned()).
                build();
    }

    public static void toDto(UpdateCompilationRequest updateCompilationRequest, Compilation compilation, Set<Event> events) {
        if (updateCompilationRequest.getTitle() != null) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }
        if (events != null) {
            compilation.setEvents(events);
        }
        compilation.setPinned(updateCompilationRequest.getPinned());

    }

//    public static CompilationDto toCompilationDto(Compilation compilation){
//        return CompilationDto.builder().
//                events(compilation.getEvents()).
//                id(compilation.getId()).
//                pinned(compilation.getPinned()).
//                title(compilation.getTitle()).
//                build();
//    }
//    public static Compilation updateCompilations(Compilation oldCompilations, NewCompilationDto newCompilations) {
//        oldCompilations.setEvents(newCompilations.getEvents());
//        oldCompilations.setPinned(newCompilations.getPinned());
//        oldCompilations.setTitle(newCompilations.getTitle());
//        return oldCompilations;
//    }
}
