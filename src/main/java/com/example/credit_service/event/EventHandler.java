package com.example.credit_service.event;

public interface EventHandler {
    String getEventType();
    void handle(OutboxEvent event);
}
