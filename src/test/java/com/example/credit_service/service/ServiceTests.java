package com.example.credit_service.service;

import com.example.credit_service.dto.CreditRequest;
import com.example.credit_service.repository.ApplicationRepo;
import com.example.credit_service.repository.EventRepo;
import com.example.credit_service.risk.RiskCalculator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// Use Mockito in this test class to enable @Mock/@InjectMocks.
@ExtendWith(MockitoExtension.class)
public class ServiceTests {
    // Create Mock dependencies. Behaviour and return values of these do nothing unless defined.
    // These are essentially test doubles to isolate service behaviour for testing.
    @Mock
    private ApplicationRepo appRepo;

    @Mock
    private EventRepo eventRepo;

    @Mock
    private RiskCalculator calc;

    // Create a real service object and inject the mocks into it.
    @InjectMocks
    private CreditApplicationService service;

    void shouldSubmitApplication(){

        // Create an input - the request DTO, a submitted application.
        CreditRequest request = new CreditRequest("Pasta", "Bake",
                "FFXIV", 50000, 15000);





    }

    void shouldGetApplication(){

    }


}
