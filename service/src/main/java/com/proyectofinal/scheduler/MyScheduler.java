package main.java.com.proyectofinal.scheduler;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class MyScheduler {
    
    @Autowired
    private RestTemplate restTemplate;

    @Value("${scheduler.url}")
    private String apiUrl;

    @Scheduled(fixedRate = 2000)
    public void scheduleTask() {
        try{
            String response = restTemplate.getForObject(apiUrl, String.class);
            System.out.println(response);
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        
    }
}
