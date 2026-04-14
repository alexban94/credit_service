package com.example.credit_service.services.application.messaging;

import com.example.credit_service.common.util.JsonUtil;
import com.example.credit_service.config.KafkaTopics;
import com.example.credit_service.event.EventRepo;
import com.example.credit_service.event.KafkaPublisher;
import com.example.credit_service.services.application.model.ApplicationOutbox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

// use @Value for the topic defined in application.yml kafka: topics
// This works as Value goes to Springs Environment to find the property specified, which automatically includes application.yml
// injects value in to the constructor variable topic
 // @Value("${kafka.topics.application}")
// Removed this to use new KafkaTopics class.

@Component
public class ApplicationPublisher extends KafkaPublisher<ApplicationOutbox> {
    public ApplicationPublisher(EventRepo<ApplicationOutbox> repo,
                                KafkaTemplate<String, String> kafka,
                                KafkaTopics topics,  JsonUtil jsonUtil) {
        super(repo, kafka, topics.getApplication(), jsonUtil);
    }
}
