package com.example.credit_service.common.event;

import java.util.UUID;

// Event produced after risk is calculated in risk module.
public record RiskEvent(
    UUID eventID,
    String appID,
    int riskScore,
    String eventType
) {
}
