package com.example.credit_service.services.risk.service;

import com.example.credit_service.common.event.ApplicationEvent;
import com.example.credit_service.common.event.EventType;
import com.example.credit_service.common.event.RiskEvent;
import com.example.credit_service.services.application.model.ApplicationOutbox;
import com.example.credit_service.services.risk.model.RiskOutbox;
import com.example.credit_service.services.risk.repository.RiskEventRepo;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.UUID;

public class RiskService {

    private final RiskCalculator calc;
    private final RiskEventRepo repo;
    private final ObjectMapper mapper;

    public RiskService(RiskCalculator calc, RiskEventRepo repo, ObjectMapper mapper){
        this.calc = calc;
        this.repo = repo;
        this.mapper = mapper;
    }

    // Calculate risk and save to outbox repo so publisher can read and send via kafka topic.
    public void calculateRisk(ApplicationEvent event){
        // use necessary data from event - salary and amount requested.
        int risk = calc.calculateRiskScore(event.salary(), event.requestedAmount());

        // no need to save risk on it's own in this service.
        // prepare new RiskEvent and wrap it in RiskOutbox, save to repo.
        RiskEvent riskEvent = new RiskEvent(event.appID() , risk);

        String json = mapper.writeValueAsString(riskEvent);

        RiskOutbox outbox = new RiskOutbox(UUID.randomUUID(), EventType.RISK.name(),
                json, event.appID() , Instant.now(), false);

        // need to ensure this saved correctly. TODO
        repo.save(outbox);
    }
}
