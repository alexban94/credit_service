package com.example.credit_service.common.event;

import java.util.UUID;

// Event produced after application is submitted and saved in application module.
public record ApplicationEvent(
        String appID,
        String firstName,
        String lastName,
        String employer,
        int salary,
        int requestedAmount
) {
}
