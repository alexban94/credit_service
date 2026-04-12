package com.example.credit_service.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

//Generic Kafka consumer class that listens to topics and receives raw kafka messages.
// Passes them onto EventRouter.
@Component
public class KafkaConsumer {

    private final EventRouter router; //determines where mmessages go.
    private final ObjectMapper objectMapper;

    public KafkaConsumer(EventRouter router, ObjectMapper mapper){
        this.router = router;
        this.objectMapper = mapper;
    }

    @KafkaListener(topics = {
            "${kafka.topics.application}",
            "${kafka.topics.risk}",
            "${kafka.topics.fraud}",
            "${kafka.topics.decision}"
    })

    // Serialize into OutboxEvent object then route it.
    public void consume(String message) throws Exception{

        OutboxEvent event = objectMapper.convertValue(message, OutboxEvent.class);
        router.route(event);

    }
}
