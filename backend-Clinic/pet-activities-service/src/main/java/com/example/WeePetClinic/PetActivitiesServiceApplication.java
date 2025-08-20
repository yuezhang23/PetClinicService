package main.java.com.example.WeePetClinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PetActivitiesServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetActivitiesServiceApplication.class, args);
    }
}
