package ru.practicum.compilation.controller;

import com.sun.nio.sctp.IllegalReceiveException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.service.ICompilationsService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/compilations")
public class CompilationsPublicController {
    private final ICompilationsService compilationsService;

    public CompilationsPublicController(ICompilationsService compilationsService) {
        this.compilationsService = compilationsService;
    }

    @GetMapping("/{compId}")
    public CompilationDto getBookingById(@PathVariable Long compId) {
        log.info("Запрос подборки с ID " + compId);
        CompilationDto compilationDto = compilationsService.getCompilationById(compId);
        log.info("Отправлен ответ " + compilationDto);
        return compilationDto;
    }

    @GetMapping
    public List<CompilationDto> getBookingByPinned(@RequestParam(defaultValue = "false") Boolean pinned,
                                                   @RequestParam(value = "from", defaultValue = "0", required = false) Integer from,
                                                   @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
        log.info("Запрос подборки с pinned " + pinned);
        if (size <= 0 || from < 0) {
            throw new IllegalReceiveException("Неверно указан параметр");
        }
        List<CompilationDto> compilationDtos = compilationsService.getCompilationsByPinned(pinned, from, size);
        log.info("Отправлен ответ " + compilationDtos);
        return compilationDtos;
    }


}
