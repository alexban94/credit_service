package com.example.credit_service.services.fraud.messaging;

import com.example.credit_service.common.event.EventType;
import com.example.credit_service.event.EventHandler;
import com.example.credit_service.event.OutboxEvent;
import com.example.credit_service.services.fraud.service.FraudService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NewApplicationFraudHandler implements EventHandler {

    private final FraudService service;
    private final ObjectMapper mapper;

    public NewApplicationFraudHandler(FraudService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public String getEventType() {
        return EventType.NEW_APPLICATION.name();
    }

    @Override
    public void handle(OutboxEvent event) {

    }
}
