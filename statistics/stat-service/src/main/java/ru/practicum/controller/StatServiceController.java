package ru.practicum.controller;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mapper.HitMapper;
import ru.practicum.model.Hit;
import ru.practicum.model.HitDto;
import ru.practicum.service.IStatService;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

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
        log.info("asdasdasd {}", hit);
        statService.addHit(hit);
    }

    @GetMapping("/stats")
    public List<HitDto> getHit(Timestamp start, Timestamp end, String uris,@RequestParam Boolean unique) {
        List<HitDto> list = statService.getHit(start, end, uris, unique);
        System.out.println(list);
        return list;
    }
}
