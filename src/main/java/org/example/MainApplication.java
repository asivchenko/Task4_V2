package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;


@SpringBootApplication
@EnableScheduling
//@Configuration
//@SpringBootApplication
public class MainApplication /*implements CommandLineRunner*/ {

    public static void main(String[] args) {
        System.out.printf("Hello Spring Boot!");
        SpringApplication.run(MainApplication.class,args);
    }
}
