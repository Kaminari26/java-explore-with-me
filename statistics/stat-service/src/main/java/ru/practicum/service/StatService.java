package ru.practicum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.mapper.HitMapper;
import ru.practicum.model.HitDto;
import ru.practicum.model.HitDtoRequest;
import ru.practicum.repository.StatRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    public void addHit(HitDtoRequest hitDtoRequest) {
        statRepository.save(HitMapper.toHitDto(hitDtoRequest));
    }

    @Override
    public List<HitDto> getHit(Timestamp start, Timestamp end, List<String> uris, Boolean unique) {
        if (start.before(end)) {
            throw new IllegalArgumentException("Неверно выбран период");
        }
        if (unique == null) {
            unique = false;
        }
        if (unique) {
            return statRepository.getStats(start.toLocalDateTime(), end.toLocalDateTime(), uris);
        }
        if (uris == null) {

            return statRepository.findByTimeIsAfterAndTimeIsBefore(start.toLocalDateTime(), end.toLocalDateTime()).stream().map(HitMapper::toHitDto).collect(Collectors.toList());
        }
        if (uris.size() >= 2) {
            List<HitDto> targerList = new ArrayList<>();
            for (String str : uris) {
                List<HitDto> dtos = statRepository.findByUriAndTimeIsAfterAndTimeIsBefore(str, start.toLocalDateTime(), end.toLocalDateTime()).stream().map(HitMapper::toHitDto).collect(Collectors.toList());
                for (HitDto hitDto : dtos) {
                    hitDto.setHits((long) dtos.size());
                }

                targerList.add(dtos.get(0));
            }
            targerList.sort((d1, d2) -> Math.toIntExact(d2.getHits() - d1.getHits()));


            return targerList;
        }
        List<HitDto> hitDtos = statRepository.findByUriAndTimeIsAfterAndTimeIsBefore(uris.get(0), start.toLocalDateTime(), end.toLocalDateTime()).stream().map(HitMapper::toHitDto).collect(Collectors.toList());
        for (HitDto hitDto : hitDtos) {
            hitDto.setHits((long) hitDtos.size());
        }
        return hitDtos;
    }
}
