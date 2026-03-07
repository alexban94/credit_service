package com.example.credit_service.event;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration // Tells Spring this class contains bean definitions.
public class KafkaConfig {

    @Bean
    public NewTopic creditApplicationTopic(){
        return TopicBuilder
                .name("credit-application-events") // topic name
                .partitions(2) // allow parallel processing, kafka distrubutes messages across partitions, so multiple consumers can process events simultaneously.
                .replicas(1) // for redundancy to protect agaisnt data loss; number of copies of each partition.
                .build();
    }
}
