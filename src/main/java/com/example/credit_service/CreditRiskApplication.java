package com.example.credit_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // For scheduling the kafka publisher.
public class CreditRiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditRiskApplication.class, args);
    }

}
