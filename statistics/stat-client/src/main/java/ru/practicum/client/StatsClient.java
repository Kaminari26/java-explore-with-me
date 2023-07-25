package ru.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.model.Hit;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {

    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public void createHit(HttpServletRequest request) {
        Hit hit = Hit.builder().app(request.getServerName())
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .time(LocalDateTime.parse(LocalDateTime.now().format(FORMATTER)))
                .build();
        post("/hit", hit);
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, String uris, Boolean unique) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("start", start.format(FORMATTER));
        parameter.put("end", end.format(FORMATTER));
        if (uris != null) {
            parameter.put("uris", uris);
        }
        if (unique) {
            parameter.put("unique", true);
        }
        return get("/stats", null, parameter);
    }
}
