package ru.practicum.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.model.HitDto;
import ru.practicum.model.HitDtoRequest;

import java.util.List;

@Service
public class StatsClient {

    private static final String ABSOLUTE_URL = "http://localhost:9090/";
    private final RestTemplate restTemplate = new RestTemplate();
    private final WebClient webClient = WebClient.builder()
            .baseUrl(ABSOLUTE_URL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();


    public void addHit(HitDtoRequest hitDtoRequest) {
        restTemplate.postForLocation(ABSOLUTE_URL + "/hit", hitDtoRequest);
    }


    public List<HitDto> getStats(String start, String end, List<String> uris, boolean unique) {
        StringBuilder urisToSend = new StringBuilder();
        for (String uri : uris) {
            urisToSend.append(uri).append(",");
        }
        ResponseEntity<List<HitDto>> response = restTemplate.exchange(
                ABSOLUTE_URL + "/stats?start={start}&end={end}&uris={uris}&unique={unique}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                start, end, urisToSend.toString(), unique);

        return response.getBody();
    }
}
