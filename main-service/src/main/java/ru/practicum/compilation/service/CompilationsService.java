package ru.practicum.compilation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationsRepository;
import ru.practicum.exeption.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompilationsService implements ICompilationsService{
    private final CompilationsRepository compilationsRepository;

    @Autowired
    public CompilationsService(CompilationsRepository compilationsRepository){
        this.compilationsRepository = compilationsRepository;
    }

    @Override
    public CompilationDto createNewCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationsRepository.save(CompilationMapper.toCompilationDto(newCompilationDto));
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public void removeCompilation(Long id) {
    compilationsRepository.deleteById(id);
    }

    @Override
    public CompilationDto getCompilationById(Long id) {
        Compilation compilation = compilationsRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Подборка не найдена"));
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public List<CompilationDto> getCompilationsByPinned(Boolean pinned, int from, int size) {
        int page = 0;
        if (from != 0) {
            page = from / size;
        }
        Pageable pageable = PageRequest.of(page, size);
        List<Compilation> compilations = compilationsRepository.findAllByPinned(pinned, pageable);
        return compilations.stream().map(CompilationMapper::toCompilationDto).collect(Collectors.toList());
    }

    @Override
    public CompilationDto updateCompilation(Long id, NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationsRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("Подборка не найдена"));
        Compilation newCompilations = compilationsRepository.save(CompilationMapper.updateCompilations(compilation, newCompilationDto));
        return CompilationMapper.toCompilationDto(newCompilations);
    }
}
