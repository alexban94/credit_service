package com.example.credit_service.event;

import com.example.credit_service.repository.EventRepo;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

//TODO: set up Kafka/MongoDB in Docker. Write JUnit tests to see if it works as intended.
// fix bug to resolve KafkaTemplate injection - no @Bean found.
// maybe to do with no Kafka broker active so the dependencies aren't created.
// set this up with Docker and also try creating a mock one with JUnit tests.
@Service
public class KafkaPublisher {

    private final EventRepo repo;
    private final KafkaTemplate<String, String> kafka;

    public KafkaPublisher(EventRepo repo, KafkaTemplate<String, String> kafka){
        this.repo = repo;
        this.kafka = kafka;
    }
    // Delay of 5 seconds after the previous execution completes.
    @Scheduled(fixedDelay = 5000)
    public void publishEvents(){
        // retrieve unprocessed events from the database.
        List<EventOutbox> events = repo.findByProcessedFalse();
        // for each event publish it and update the mongoDB document to indicate it's already been published.
        for(EventOutbox event: events) {

            // published on the topic "credit-application-events"
            kafka.send("credit-application-events", event.getId().toString(), event.getData());
            event.setProcessed(true); // event now published.

            // save event again.
            // when using save Spring Data uses @ID field to determine whether it's INSERT or UPDATE.
            repo.save(event);

        }
    }

}
