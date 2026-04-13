package com.example.credit_service.services.risk.messaging;

import com.example.credit_service.common.event.ApplicationEvent;
import com.example.credit_service.common.event.EventType;
import com.example.credit_service.common.event.RiskEvent;
import com.example.credit_service.event.EventHandler;
import com.example.credit_service.event.OutboxEvent;
import com.example.credit_service.services.application.service.ApplicationService;
import com.example.credit_service.services.risk.service.RiskService;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class NewApplicationRiskHandler implements EventHandler {

    private final RiskService service;
    private final ObjectMapper mapper;

    public NewApplicationRiskHandler(RiskService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public String getEventType() {
        return EventType.NEW_APPLICATION.name();
    }

    // Convert Outbox event payload into Risk event, then process it by calling the correct service method.
    @Override
    public void handle(OutboxEvent e) {
        ApplicationEvent event = mapper.convertValue(e.getPayload(), ApplicationEvent.class);
        service.calculateRisk(event);
    }
}