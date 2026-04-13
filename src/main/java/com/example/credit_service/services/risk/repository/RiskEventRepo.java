package com.example.credit_service.services.risk.repository;

import com.example.credit_service.event.EventRepo;
import com.example.credit_service.services.risk.model.RiskOutbox;

public interface RiskEventRepo extends EventRepo<RiskOutbox> {
}
