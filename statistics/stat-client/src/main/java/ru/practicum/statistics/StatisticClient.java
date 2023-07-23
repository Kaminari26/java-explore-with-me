//package ru.practicum.statistics;
//
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import org.springframework.web.util.DefaultUriBuilderFactory;
//
//import ru.practicum.client.BaseClient;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//;
//import ru.practicum.model.Hit;
//
//
//
//import java.sql.Timestamp;
//
//import java.util.Map;
//
//
//
//@Service
//public class StatisticClient extends BaseClient {
//
//    private static final String API_PREFIX = "/";
//    public StatisticClient(@Value("${stat-service.url}") String serverUrl, RestTemplateBuilder builder) {
//        super(
//                builder
//                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
//                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
//                        .build()
//        );
//    }
//
//    public ResponseEntity<Object> createHit(Hit hitDto) {
//        return post("hit", hitDto);
//    }
//
//    public ResponseEntity<Object> getStats(Timestamp start, Timestamp end, Boolean unique, String uris) {
//        Map<String, Object> parameters = Map.of(
//                "start", start,
//                "end", end,
//                "unique", unique,
//                "uris", uris
//        );
//        return get("stats?start={start}&end={end}&unique{unique}&uris{uris}",null, parameters );
//    }
//}
