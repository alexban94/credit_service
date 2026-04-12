package com.example.credit_service.common.event;

import java.util.UUID;

public record FraudEvent(
        UUID eventID,
        String appID,
        boolean fraudSuspect,
        String eventType
) {
}
