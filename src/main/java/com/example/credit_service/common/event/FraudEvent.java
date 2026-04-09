package com.example.credit_service.common.event;

import java.util.UUID;

public record FraudEvent(
        UUID eventId,
        String appID,
        boolean fraudSuspect
) {
}
