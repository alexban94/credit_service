package com.example.credit_service.service;

import com.example.credit_service.model.CustomerApplication;
import com.example.credit_service.dto.CreditRequest;
import com.example.credit_service.dto.CreditResponse;
import com.example.credit_service.exception.ApplicationNotFoundException;
import com.example.credit_service.repository.ApplicationRepo;
import com.example.credit_service.risk.RiskCalculator;
import org.springframework.stereotype.Service;

// Co-ordinates everything but doesn't deal directly with the HTTP request/security.
@Service
public class CreditApplicationService {

    private final RiskCalculator calc;
    private final ApplicationRepo repo;

    //Dependency Injection of shared objects.
    public CreditApplicationService(RiskCalculator calc, ApplicationRepo repo){
        this.repo = repo;
        this.calc = calc;
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
        CustomerApplication saved = repo.save(app);

        //Return the DTO CreditResponse (for when its called by the controller for a POST request).
        return new CreditResponse(saved.getId(), saved.getDecision(), saved.getRiskScore());
    }

    public CreditResponse getApplication(String id){
        //Lambda expression to throw a new ApplicationNotFound exception if the application id does not exist in MongoDB.
        CustomerApplication app = repo.findById(id).orElseThrow(()-> new ApplicationNotFoundException(id));

        //If application is found then return it.
        return new CreditResponse(app.getId(), app.getDecision(), app.getRiskScore());

    }
}
