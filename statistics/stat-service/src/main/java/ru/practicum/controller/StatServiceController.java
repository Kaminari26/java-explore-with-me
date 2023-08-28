package ru.practicum.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.HitDto;
import ru.practicum.model.HitDtoRequest;
import ru.practicum.service.IStatService;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RestController
@Slf4j
@RequestMapping
public class StatServiceController {
    private final IStatService statService;


    @Autowired
    public StatServiceController(IStatService statService) {
        this.statService = statService;
    }

    @PostMapping("/hit")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createItem(@RequestBody HitDtoRequest hitDtoRequest) {
        log.info("hit {}", hitDtoRequest);
        statService.addHit(hitDtoRequest);
        log.info("Hit Успешно добавлен");
    }

    @GetMapping("/stats")
    public List<HitDto> getHit(@RequestParam @NonNull Timestamp start,
                               @RequestParam @NonNull Timestamp end,
                               @RequestParam(required = false) List<String> uris,
                               @RequestParam(defaultValue = "false") boolean unique) {
        log.info("start {} end {} uris {} unique {}", start, end, uris, unique);
        List<HitDto> dtos = statService.getHit(start, end, uris, unique);
        log.info("Отправлен ответ: {} ", dtos);
        return dtos;
    }
}
