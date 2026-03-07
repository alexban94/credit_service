package com.example.credit_service.event;

import java.util.UUID;

// Record class to represent the Event as immutable inside the application.
public record EventRecord(
        UUID eventID,
        String appID,
        String firstName,
        String lastName,
        String employer,
        int riskScore,
        int salary,
        String decision
) {
}
