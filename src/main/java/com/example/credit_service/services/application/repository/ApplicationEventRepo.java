package com.example.credit_service.services.application.repository;

import com.example.credit_service.event.EventRepo;
import com.example.credit_service.services.application.model.ApplicationOutbox;

// necessary query method in super class. This subclass allows the service to have a specific event repo and collection.
public interface ApplicationEventRepo extends EventRepo<ApplicationOutbox> {
}
