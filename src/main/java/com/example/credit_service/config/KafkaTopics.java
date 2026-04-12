package com.example.credit_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "kafka.topics")
public class KafkaTopics {

    //ConfigurationProperties will create this from the kafka.topics entries in application.yml
    private String application;
    private String risk;
    private String fraud;
    private String decision;

}
