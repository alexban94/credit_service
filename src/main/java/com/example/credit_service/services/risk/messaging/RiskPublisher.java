package com.example.credit_service.services.risk.messaging;

import com.example.credit_service.event.EventRepo;
import com.example.credit_service.event.KafkaPublisher;
import com.example.credit_service.services.risk.model.RiskOutbox;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class RiskPublisher extends KafkaPublisher<RiskOutbox> {
    public RiskPublisher(EventRepo<RiskOutbox> repo, KafkaTemplate<String, String> kafka, String topic, ObjectMapper objectMapper) {
        super(repo, kafka, topic, objectMapper);
    }
}
