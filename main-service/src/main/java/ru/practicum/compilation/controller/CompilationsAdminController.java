package ru.practicum.compilation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
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
    public CompilationDto createCompilation(@RequestBody NewCompilationDto newCompilationDto) {
        log.info("Пришел запрос Post /admin/compilations");
        CompilationDto compilationDto = compilationsService.createNewCompilation(newCompilationDto);
        log.info("Отправлен ответ " + compilationDto);
        return compilationDto;
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        log.info("Удаление подборки " + compId);
        compilationsService.removeCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateItem(@PathVariable Long compId,
            @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Обновление подборки с id: {} dto: {}", compId, newCompilationDto);
        CompilationDto compilationDto = compilationsService.updateCompilation(compId, newCompilationDto);
        log.info("Обновленный предмет " + compilationDto);
        return compilationDto;
    }
}
