package ru.practicum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.mapper.HitMapper;
import ru.practicum.model.Hit;
import ru.practicum.model.HitDto;
import ru.practicum.repository.StatRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatService implements IStatService {
    private final StatRepository statRepository;
    //StatisticClient statisticClient;

    @Autowired
    public StatService(StatRepository statRepository) {
        this.statRepository = statRepository;
    }


    @Override
    public void addHit(Hit hit) {
        hit.setTime(LocalDateTime.now());
        Hit hit1 = statRepository.save(hit);
    }


    //Клиент = подключение к сервису
    //клиент воруем из прошлого, это тоже самое что гатевай, дто отдельно делаем, ниче никуда не передаем, на ретерне сервиса сначала дто(тут клиент), как то импортируем сбда все эти хуйни, и тьут их вызываем
    @Override
    public List<HitDto> getHit(Timestamp start, Timestamp end, String uris, Boolean unique) {
        if (unique == null) {
            unique = false;
        }
        if (unique) {
            return statRepository.getStats(start.toLocalDateTime(), end.toLocalDateTime(), uris);
        }
        if (uris == null) {
            return statRepository.findByTimeIsAfterAndTimeIsBefore(start.toLocalDateTime(), end.toLocalDateTime()).stream().map(HitMapper::toHitDto).collect(Collectors.toList());
        }
        return statRepository.findByUriAndTimeIsAfterAndTimeIsBefore(uris, start.toLocalDateTime(), end.toLocalDateTime()).stream().map(HitMapper::toHitDto).collect(Collectors.toList());
        // return mapper.asdasd(asdasd);
        // List<Hit> asd = statisticClient.getStats(start,end,unique);
        //  hits.stream().map(HitMapper :: toHitDto ).collect(Collectors.toList()) ;

    }
}
