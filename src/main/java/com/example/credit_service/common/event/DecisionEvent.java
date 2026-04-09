package com.example.credit_service.common.event;

import java.util.UUID;

public record DecisionEvent(
        UUID eventId,
        String applicationId,
        String decision,
        int riskScore
) {
}
