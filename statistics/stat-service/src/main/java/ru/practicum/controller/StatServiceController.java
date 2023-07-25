package ru.practicum.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.Hit;
import ru.practicum.model.HitDto;
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
    public void createItem(@RequestBody Hit hit) {
        log.info("hit {}", hit);
        statService.addHit(hit);
        log.info("Hit Успешно добавлен");
    }

    @GetMapping("/stats")
    public List<HitDto> getHit(Timestamp start, Timestamp end, String uris, Boolean unique) {
        log.info("start {} end {} uris {} unique {}", start, end, uris, unique);
        List<HitDto> list = statService.getHit(start, end, uris, unique);
        log.info("Отправлен ответ: {} ", list);
        return list;
    }
}
