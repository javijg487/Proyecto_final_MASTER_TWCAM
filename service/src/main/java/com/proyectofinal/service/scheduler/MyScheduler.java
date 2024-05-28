package com.proyectofinal.service.scheduler;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class MyScheduler {
    
    @Autowired
    private RestTemplate restTemplate;

    @Value("${scheduler.url}")
    private String apiUrl;

    @Value("${access.token}")
    private String token;

    @Scheduled(fixedRate = 20000)
    public void scheduleTask() {
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
            System.out.println(response.getStatusCode() + "\n" + response.getBody());
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
