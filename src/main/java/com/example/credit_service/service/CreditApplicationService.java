package com.example.credit_service.service;

import com.example.credit_service.event.EventOutbox;
import com.example.credit_service.event.EventRecord;
import com.example.credit_service.model.CustomerApplication;
import com.example.credit_service.dto.CreditRequest;
import com.example.credit_service.dto.CreditResponse;
import com.example.credit_service.exception.ApplicationNotFoundException;
import com.example.credit_service.repository.ApplicationRepo;
import com.example.credit_service.repository.EventRepo;
import com.example.credit_service.risk.RiskCalculator;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.UUID;

// Co-ordinates everything but doesn't deal directly with the HTTP request/security.
@Service
public class CreditApplicationService {

    private final RiskCalculator calc;
    private final ApplicationRepo appRepo;
    private final EventRepo eventRepo;
    private final ObjectMapper objectMapper;

    //Dependency Injection of shared objects.
    public CreditApplicationService(RiskCalculator calc, ApplicationRepo appRepo, EventRepo eventRepo, ObjectMapper objectMapper){
        this.appRepo = appRepo;
        this.calc = calc;
        this.eventRepo = eventRepo;
        this.objectMapper = objectMapper;
    }

    //Called from the CreditApplicationController (REST API) to handle logic
    public CreditResponse submitApplication(CreditRequest request) {

        int score = calc.calculateRiskScore(request.annualIncome(), request.requestAmount());
        String decision = calc.decision(score);

        //ID is null as it will be created by MongoDB later. Create application model object.
        CustomerApplication app = new CustomerApplication(null, request.firstName(), request.lastName(),
                request.employer(), request.requestAmount(), request.annualIncome(), score, decision);

        // Save in MongoDB. Use new variable to indicate it's the persisted version of object used with MongoDB.
        // Rule of thumb is to treat a save() function as returning the authorative persisted state. depending on
        // implementation it could mutate the original object or return a new instance.
        CustomerApplication saved = appRepo.save(app);
        // Create a new event - immutable business fact that the application has been saved.
        EventRecord event = new EventRecord(
                UUID.randomUUID(),
                saved.getId(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getEmployer(),
                saved.getRiskScore(),
                saved.getAnnualIncome(),
                saved.getDecision()
                );
        String json = objectMapper.writeValueAsString(event); //convert event to json for mongoDB.
        EventOutbox outbox = new EventOutbox(UUID.randomUUID(), "CreditApplicationSaved",
                json, Instant.now(), false); // create outbox event for mongoDB. the record is the data/payload, this class is a 'wrapper' for it.
        eventRepo.save(outbox); // save it to the event repo.
        // now the application is saved and the event is saved so neither can be lost. this will be handled separately by kafka.




        //Return the DTO CreditResponse (for when its called by the controller for a POST request).
        return new CreditResponse(saved.getId(), saved.getDecision(), saved.getRiskScore());
    }

    public CreditResponse getApplication(String id){
        //Lambda expression to throw a new ApplicationNotFound exception if the application id does not exist in MongoDB.
        CustomerApplication app = appRepo.findById(id).orElseThrow(()-> new ApplicationNotFoundException(id));

        //If application is found then return it.
        return new CreditResponse(app.getId(), app.getDecision(), app.getRiskScore());

    }
}
