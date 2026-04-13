package com.example.credit_service.services.risk.model;

import com.example.credit_service.event.OutboxEvent;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "risk_events")
public class RiskOutbox extends OutboxEvent {

    public RiskOutbox(UUID id, String eventType, String payload, String appID, Instant timestamp, boolean processed) {
        super(id, eventType, payload, appID, timestamp, processed);
    }
}
