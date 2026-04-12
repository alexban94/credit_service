package com.example.credit_service.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


@Configuration // Tells Spring this class contains bean definitions.
public class KafkaConfig {

    // Create all necessary topics.
    //TODO: will need to separate this when extracting into microservice.

    @Bean
    public NewTopic applicationTopic(KafkaTopics topics){
        return TopicBuilder
                .name(topics.getApplication()) // topic name
                .partitions(2) // allow parallel processing, kafka distrubutes messages across partitions, so multiple consumers can process events simultaneously.
                .replicas(1) // for redundancy to protect agaisnt data loss; number of copies of each partition.
                .build();
    }

    @Bean
    public NewTopic riskTopic(KafkaTopics topics){
        return TopicBuilder
                .name(topics.getRisk())
                .partitions(2)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic fraudTopic(KafkaTopics topics){
        return TopicBuilder
                .name(topics.getFraud())
                .partitions(2)
                .build();
    }

    @Bean
    public NewTopic decisionTopic(KafkaTopics topics){
        return TopicBuilder
                .name(topics.getDecision())
                .partitions(2)
                .replicas(1)
                .build();
    }
}
