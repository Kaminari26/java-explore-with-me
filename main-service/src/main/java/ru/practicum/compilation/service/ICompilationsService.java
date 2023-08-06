package ru.practicum.compilation.service;

import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;

import java.util.List;

public interface ICompilationsService {
    CompilationDto createNewCompilation(NewCompilationDto newCompilationDto);
    void removeCompilation(Long id);
    CompilationDto getCompilationById(Long id);
    List<CompilationDto> getCompilationsByPinned(Boolean pinned, int from, int size);
    CompilationDto updateCompilation(Long id, NewCompilationDto newCompilationDto);
}
