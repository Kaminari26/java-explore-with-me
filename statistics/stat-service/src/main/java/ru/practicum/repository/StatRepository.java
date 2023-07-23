package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Hit;
import ru.practicum.model.HitDto;

import java.time.LocalDateTime;
import java.util.List;

;

@Repository
public interface StatRepository extends JpaRepository<Hit, Long> {

    List<Hit> findByTimeIsAfterAndTimeIsBefore(LocalDateTime start, LocalDateTime end);

    List<Hit> findByUriAndTimeIsAfterAndTimeIsBefore(String uri, LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.model.HitDto(s.app, s.uri, COUNT(DISTINCT s.ip)) " +
            "FROM Hit as s " +
            "WHERE s.time BETWEEN :start and :end " +
            "AND s.uri IN :uris " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(DISTINCT s.ip) DESC")
    List<HitDto> getStats(LocalDateTime start, LocalDateTime end, String uris);


}
