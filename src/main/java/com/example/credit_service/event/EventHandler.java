package com.example.credit_service.event;

// Interface for classes which will access OutboxEvent payload and convert it to the correct Event type
public interface EventHandler {
    String getEventType();
    void handle(OutboxEvent event);
}
