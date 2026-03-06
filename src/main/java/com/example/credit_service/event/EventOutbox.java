package com.example.credit_service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

// Class to represent the kafka event with the Outbox pattern to ensure no events are lost.
@Getter
@Setter
@AllArgsConstructor
@Document(collection="events") // To be stored in separate 'events' collection.
public class EventOutbox {

    @Id
    private UUID id; //unique ID for the outbox event.

    private String eventType; //Type of event
    private String data; // actual event data as JSON.

    private Instant timestamp; //time created

    private boolean processed; //If event has been processed yet or not.

}
