package com.example.credit_service.repository;

import com.example.credit_service.event.EventOutbox;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface EventRepo extends MongoRepository<EventOutbox, UUID> {
    //TODO: add method to query unprocessed events.
}
