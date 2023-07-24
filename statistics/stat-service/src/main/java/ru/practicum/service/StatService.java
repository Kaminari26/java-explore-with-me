package ru.practicum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.mapper.HitMapper;
import ru.practicum.model.Hit;
import ru.practicum.model.HitDto;
import ru.practicum.repository.StatRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatService implements IStatService {
    private final StatRepository statRepository;

    @Autowired
    public StatService(StatRepository statRepository) {
        this.statRepository = statRepository;
    }


    @Override
    public void addHit(Hit hit) {
        hit.setTime(LocalDateTime.now());
        statRepository.save(hit);
    }

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
        if (uris.contains(",")) {
            List<String> listUris = Arrays.stream(uris.split(",")).collect(Collectors.toList());
            List<HitDto> targerList = new ArrayList<>();
            for (String str : listUris) {
                List<HitDto> list = statRepository.findByUriAndTimeIsAfterAndTimeIsBefore(str, start.toLocalDateTime(), end.toLocalDateTime()).stream().map(HitMapper::toHitDto).collect(Collectors.toList());
                for (HitDto hitDto : list) {
                    hitDto.setHits((long) list.size());
                }

                targerList.add(list.get(0));
            }
            targerList.sort((d1, d2) -> Math.toIntExact(d2.getHits() - d1.getHits()));


            return targerList;
        }
        List<HitDto> list = statRepository.findByUriAndTimeIsAfterAndTimeIsBefore(uris, start.toLocalDateTime(), end.toLocalDateTime()).stream().map(HitMapper::toHitDto).collect(Collectors.toList());
        for (HitDto hitDto : list) {
            hitDto.setHits((long) list.size());
        }
        return list;
    }
}
