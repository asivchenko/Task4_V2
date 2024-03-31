package org.example;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MainApplication /*implements CommandLineRunner*/ {

    public static void main(String[] args) {
        System.out.printf("Hello Spring Boot!");
        SpringApplication.run(MainApplication.class,args);
    }
}
