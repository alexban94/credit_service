package com.example.credit_service.dto;

public record CreditResponse(
        String appID, //uniqueID for the application
        String decision, //approved or denied.
        int riskScore //calculated risk done by business logic in a service.
){
}
