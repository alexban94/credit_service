package com.example.credit_service.service;

import com.example.credit_service.dto.CreditRequest;
import com.example.credit_service.dto.CreditResponse;
import com.example.credit_service.risk.RiskCalculator;
import org.springframework.stereotype.Service;

@Service
public class CreditApplicationService {

    private final RiskCalculator calc;
    private final ApplicationRepository repo;

    public CreditApplicationService(RiskCalculator calc, ApplicationRepository repo){
        this.repo = repo;
        this.calc = calculator;
    }

    public CreditResponse submitApplication(CreditRequest request) {
        return null;
    }
}
