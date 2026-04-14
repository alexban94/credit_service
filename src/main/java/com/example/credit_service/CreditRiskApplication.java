package com.example.credit_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ConfigurationPropertiesScan // to find KafkaTopics class
@EnableScheduling // For scheduling the kafka publisher.
public class CreditRiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditRiskApplication.class, args);
    }

}
