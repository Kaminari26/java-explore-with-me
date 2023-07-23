package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import ru.practicum.statistics.StatisticClient;

@SpringBootApplication
public class StatServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(StatServiceMain.class, args);
      //  new StatisticClient("${stat-service.url}", new RestTemplateBuilder());
      //  new HitMapper();
    }
}
