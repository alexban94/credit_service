package com.example.credit_service.event;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.UUID;

// Make this a generic repo interface as the generic type of MongoRepository<T, UUID> determines the collection used.
// So can't use subclasses of OutboxEvent class directly and will need to extend this for each service. Enforce generics with bounds so it must an OutboxEvent subclass.
@NoRepositoryBean //Stop SpringData creating concrete implementation of this generic repo interface as it will fail.
public interface EventRepo<T extends OutboxEvent> extends MongoRepository<T, UUID> {
    //TODO: test query for unprocessed events.
    // Spring generates implementation of interface at runtime.
    // IT aslso provides implemntation for save(), findById(), delete() etc.
    // it also implents this method based on it's name; find (query) by (start of condition) (field) (value)
    List<T> findByProcessedFalse();
}

