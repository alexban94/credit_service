package com.example.credit_service.services.application.messaging;

import com.example.credit_service.common.event.DecisionEvent;
import com.example.credit_service.common.event.EventType;
import com.example.credit_service.event.EventHandler;
import com.example.credit_service.event.OutboxEvent;
import com.example.credit_service.services.application.service.ApplicationService;
import tools.jackson.databind.ObjectMapper;

public class DecisionUpdateHandler implements EventHandler {

    private final ObjectMapper mapper;
    private final ApplicationService service;

    public DecisionUpdateHandler(ApplicationService service, ObjectMapper mapper){
        this.mapper = mapper;
        this.service = service;
    }

    @Override
    public String getEventType(){
        return EventType.DECISION.name();
    }

    @Override
    public void handle(OutboxEvent e){
        DecisionEvent event = mapper.convertValue(e.getPayload(), DecisionEvent.class);
        service.updateDecision(event);
    }

}
