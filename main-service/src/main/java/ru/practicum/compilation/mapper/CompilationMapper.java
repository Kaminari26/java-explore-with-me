package ru.practicum.compilation.mapper;

import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.model.Compilation;

public class CompilationMapper {
    public static Compilation toCompilationDto(NewCompilationDto newCompilationDto) {
        return Compilation.builder().
                id(null).title(newCompilationDto.getTitle()).
                events(newCompilationDto.getEvents()).
                pinned(newCompilationDto.getPinned()).
                build();
    }

    public static CompilationDto toCompilationDto(Compilation compilation){
        return CompilationDto.builder().
                events(compilation.getEvents()).
                id(compilation.getId()).
                pinned(compilation.getPinned()).
                title(compilation.getTitle()).
                build();
    }
    public static Compilation updateCompilations(Compilation oldCompilations, NewCompilationDto newCompilations) {
        oldCompilations.setEvents(newCompilations.getEvents());
        oldCompilations.setPinned(newCompilations.getPinned());
        oldCompilations.setTitle(newCompilations.getTitle());
        return oldCompilations;
    }
}
