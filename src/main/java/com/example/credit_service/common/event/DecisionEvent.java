package com.example.credit_service.common.event;

import java.util.UUID;

public record DecisionEvent(
        String appID,
        String decision,
        int riskScore
) {
}
