package com.example.credit_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@Getter
@Setter
@ConfigurationProperties(prefix = "kafka.topics")
public class KafkaTopics {

    //ConfigurationProperties will create this from the kafka.topics entries in application.yml. Reads YAML and maps values into this java object.
    private String application;
    private String risk;
    private String fraud;
    private String decision;

}
