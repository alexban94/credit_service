package com.example.credit_service.services.application.service;

import com.example.credit_service.common.event.ApplicationEvent;
import com.example.credit_service.common.event.DecisionEvent;
import com.example.credit_service.common.event.EventType;
import com.example.credit_service.common.event.RiskEvent;
import com.example.credit_service.common.util.JsonUtil;
import com.example.credit_service.services.application.model.ApplicationOutbox;
import com.example.credit_service.services.application.model.CustomerApplication;
import com.example.credit_service.common.dto.CreditRequest;
import com.example.credit_service.common.dto.CreditResponse;
import com.example.credit_service.common.exception.ApplicationNotFoundException;
import com.example.credit_service.services.application.repository.ApplicationEventRepo;
import com.example.credit_service.services.application.repository.ApplicationRepo;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.UUID;

// Co-ordinates everything but doesn't deal directly with the HTTP request/security.
@Service
public class ApplicationService {

    private final ApplicationRepo appRepo;
    private final ApplicationEventRepo eventRepo;
    private final JsonUtil jsonUtil;

    //Dependency Injection of shared objects.
    public ApplicationService(ApplicationRepo appRepo, ApplicationEventRepo eventRepo, JsonUtil jsonUtil){
        this.appRepo = appRepo;
        this.eventRepo = eventRepo;
        this.jsonUtil = jsonUtil;
    }

    //Called from the CreditApplicationController (REST API) to handle logic
    public CreditResponse submitApplication(CreditRequest request) {

        //ID is null as it will be created by MongoDB later. Create application model object.
        CustomerApplication app = new CustomerApplication(null, request.firstName(), request.lastName(),
                request.employer(), request.requestAmount(), request.annualIncome(), 0, "PENDING", false, false);

        // Save in MongoDB. Use new variable to indicate it's the persisted version of object used with MongoDB.
        // Rule of thumb is to treat a save() function as returning the authorative persisted state. depending on
        // implementation it could mutate the original object or return a new instance.
        CustomerApplication saved = appRepo.save(app);
        // Create a new event - immutable business fact that the application has been saved.
        ApplicationEvent event = new ApplicationEvent(
                saved.getId(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getEmployer(),
                saved.getAnnualIncome(),
                saved.getRequestAmount()
                );
        String json = jsonUtil.toJson(event); //convert event to json for mongoDB.
        ApplicationOutbox outbox = new ApplicationOutbox(UUID.randomUUID(), EventType.NEW_APPLICATION.name(),
                json, event.appID() ,Instant.now(), false); // create outbox event for mongoDB. the record is the data/payload, this class is a 'wrapper' for it.
        eventRepo.save(outbox); // save it to the event repo.
        // now the application is saved and the event is saved so neither can be lost. this will be handled separately by the kafkaPublisher.




        //Return the DTO CreditResponse (for when its called by the controller for a POST request).
        return new CreditResponse(saved.getId(), saved.getDecision(), saved.getRiskScore());
    }

    public CreditResponse getApplication(String id){
        //Lambda expression to throw a new ApplicationNotFound exception if the application id does not exist in MongoDB.
        CustomerApplication app = appRepo.findById(id).orElseThrow(()-> new ApplicationNotFoundException(id));

        //If application is found then return it.
        return new CreditResponse(app.getId(), app.getDecision(), app.getRiskScore());

    }

    public void updateRisk(RiskEvent event){
        // Update customer application in database, check if application is complete. If so emit event.
        // TODO: implementation and send kafka message.
    }

    public void updateDecision(DecisionEvent event){
        // Update decision in customer application.
        // TODO: implementation and send kafka maessage.
    }
}
