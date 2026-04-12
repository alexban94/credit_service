package com.example.credit_service.services.application.model;

import com.example.credit_service.event.OutboxEvent;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

// Use this collection
@Document(collection = "application_events")
//@SuperBuilder //implement constructors that pass arguments superclass. Note inheritance does not inherit constructors so concrete implementation or using this to convert to a builder is necessary.
public class ApplicationOutbox extends OutboxEvent {

    public ApplicationOutbox(UUID id, String eventType, String payload, String appID, Instant timestamp, boolean processed) {
        super(id, eventType, payload, appID, timestamp, processed);
    }
}
