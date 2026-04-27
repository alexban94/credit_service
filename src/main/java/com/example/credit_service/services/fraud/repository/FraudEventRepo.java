package com.example.credit_service.services.fraud.repository;

import com.example.credit_service.event.EventRepo;
import com.example.credit_service.services.fraud.model.FraudOutbox;

public interface FraudEventRepo extends EventRepo<FraudOutbox> {
}
