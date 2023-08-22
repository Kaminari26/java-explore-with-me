package ru.practicum.compilation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.service.ICompilationsService;

@RestController
@Slf4j
@RequestMapping("/admin/compilations")
public class CompilationsAdminController {
    private final ICompilationsService compilationsService;

    @Autowired
    public CompilationsAdminController(ICompilationsService compilationsService){
        this.compilationsService = compilationsService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@RequestBody NewCompilationDto newCompilationDto) {
        log.info("Пришел запрос Post /admin/compilations");
        CompilationDto compilationDto = compilationsService.createNewCompilation(newCompilationDto);
        log.info("Отправлен ответ " + compilationDto);
        return compilationDto;
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        log.info("Удаление подборки " + compId);
        compilationsService.removeCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateItem(@PathVariable Long compId,
            @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        log.info("Обновление подборки с id: {} dto: {}", compId, updateCompilationRequest);
        CompilationDto compilationDto = compilationsService.updateCompilation(compId, updateCompilationRequest);
        log.info("Обновленный предмет " + compilationDto);
        return compilationDto;
    }
}
