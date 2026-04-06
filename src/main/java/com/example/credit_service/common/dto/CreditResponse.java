package com.example.credit_service.common.dto;

public record CreditResponse(
        String appID, //uniqueID for the application
        String decision, //approved or denied.
        int riskScore //calculated risk done by business logic in a service.
){
}
