package com.example.credit_service.services.application.messaging;

import com.example.credit_service.common.event.EventType;
import com.example.credit_service.common.event.RiskEvent;
import com.example.credit_service.event.EventHandler;
import com.example.credit_service.event.OutboxEvent;
import com.example.credit_service.services.application.service.ApplicationService;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RiskUpdateHandler implements EventHandler {

    private final ApplicationService service;
    private final ObjectMapper mapper;

    public RiskUpdateHandler(ApplicationService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public String getEventType() {
        return EventType.RISK.name();
    }

    // Convert Outbox event payload into Risk event, then process it by calling the correct service method.
    @Override
    public void handle(OutboxEvent e) {
        RiskEvent event = mapper.convertValue(e.getPayload(), RiskEvent.class);
        service.updateRisk(event);

    }
}
