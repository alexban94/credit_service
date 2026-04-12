package com.example.credit_service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.UUID;

// Class to represent the kafka event with the Outbox pattern to ensure no events are lost.
// i.e. a wrapper used to store events safely in MongoDB.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class OutboxEvent {

    @Id
    private UUID id; //unique ID for the outbox event.

    private String eventType; //Type of event
    private String payload; // actual event data as JSON.

    private String appID; // to use as key when publishing the event via kafka.

    private Instant timestamp; //time created

    private boolean processed; //If event has been processed yet or not.

}
