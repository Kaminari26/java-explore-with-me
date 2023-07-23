//package ru.practicum.statistics;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import ru.practicum.model.Hit;
//import ru.practicum.model.HitDto;
//
//import javax.validation.Valid;
//import java.sql.Timestamp;
//import java.util.List;
//
//@RestController
//@RequestMapping
//@RequiredArgsConstructor
//@Slf4j
//public class StatisticController {
//    private final StatisticClient statisticClient;
//    @PostMapping("/hit")
//    public ResponseEntity<Object> createHit(@Valid @RequestBody Hit hit) {
//        log.info("Creating Hit {}", hit);
//       return statisticClient.createHit(hit);
//
//    }
//
//     @GetMapping("/stats")
//    public ResponseEntity<Object> getStats(Timestamp start, Timestamp end, Boolean unique, String uris) {
//        log.info("get Stats ,  start {}, end {}, unique {}, uris {}",start, end, unique, uris);
//        return statisticClient.getStats( start, end,unique, uris);
//    }
//}
