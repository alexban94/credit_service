package com.example.credit_service.repository;

import com.example.credit_service.event.EventOutbox;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface EventRepo extends MongoRepository<EventOutbox, UUID> {
    //TODO: test query for unprocessed events.
    // Spring generates implementation of interface at runtime.
    // IT aslso provides implemntation for save(), findById(), delete() etc.
    // it also implents this method based on it's name; find (query) by (start of condition) (field) (value)
    List<EventOutbox> findByProcessedFalse();
}

