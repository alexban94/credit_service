package com.example.credit_service.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

//TODO: set up Kafka/MongoDB in Docker. Write JUnit tests to see if it works as intended.
// fix bug to resolve KafkaTemplate injection - no @Bean found.
// maybe to do with no Kafka broker active so the dependencies aren't created.
// set this up with Docker and also try creating a mock one with JUnit tests.

// Converted to be generic so a publisher can be created for each service - need one for each so they can be separated more easily lately
// and can use the correct topic/repo for the service. T must extend OutboxEvent so it forces T to be a subclass that will have the correct methods.
public class KafkaPublisher<T extends OutboxEvent> {

    private final EventRepo<T> repo;
    private final String topic;
    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper objectMapper;

    public KafkaPublisher(EventRepo<T> repo, KafkaTemplate<String, String> kafka, String topic, ObjectMapper objectMapper){
        this.repo = repo;
        this.topic = topic;
        this.kafka = kafka;
        this.objectMapper = objectMapper;
    }
    // Delay of 5 seconds after the previous execution completes.
    @Scheduled(fixedDelay = 5000)
    public void publishEvents(){
        // retrieve unprocessed events from the database.
        List<T> events = repo.findByProcessedFalse();
        // for each event publish it and update the mongoDB document to indicate it's already been published.
        for(T event: events) {

            // published on the topic "credit-application-events"
            kafka.send(topic, event.getAppID(), objectMapper.writeValueAsString(event));
            event.setProcessed(true); // event now published.

            // save event again.
            // when using save Spring Data uses @ID field to determine whether it's INSERT or UPDATE.
            repo.save(event);

        }
    }

}
