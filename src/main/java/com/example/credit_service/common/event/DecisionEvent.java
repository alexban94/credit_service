package com.example.credit_service.common.event;

import java.util.UUID;

public record DecisionEvent(
        UUID eventID,
        String appID,
        String decision,
        int riskScore,
        String eventType

) {
}
