package com.example.credit_service.service;

import com.example.credit_service.dto.CreditRequest;
import com.example.credit_service.dto.CreditResponse;
import com.example.credit_service.repository.ApplicationRepo;
import com.example.credit_service.repository.EventRepo;
import com.example.credit_service.risk.RiskCalculator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

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

    void shouldApproveApplication(){

        // Create an input - the request DTO, a submitted application.
        CreditRequest request = new CreditRequest("Pasta", "Bake",
                "FFXIV", 50000, 10000);

        // Define mock behaviour.
        when(calc.calculateRiskScore(50000, 10000)).thenReturn(80); //return this when method is called with these inputs.
        when(calc.decision(80)).thenReturn("APPROVED");

        // accept any input and return it dynamically with thenAnswer.
        // object passed into thenAnswer() is InvocationOnMock object. use it to get first argument and return it like the save method does.
        when(appRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        // Call the method being tested.
        CreditResponse response = service.submitApplication(request);

        // Check if method output response has the correct values.
        // Assertions. first param is message shown if it fails.
        assertEquals("Decision should be APPROVED","APPROVED", response.decision());
        assertEquals("Risk Score should be 80", response.riskScore(), 80);
        assertNotNull("DTO appID should be set", response.appID());

        // Verify - check methods were called on the mocks. Useful if bugs are introduced e.g. method call removed when modifying class.
        verify(calc).calculateRiskScore(50000, 10000);
        verify(calc).decision(80);
        verify(appRepo).save(any());
        verify((eventRepo)).save(any());

   }

    void shouldRejectApplication(){
        CreditRequest request = new CreditRequest("Pasta", "Bake",
                "FFXIV", 10000, 50000);

        // Define mock behaviour.
        when(calc.calculateRiskScore(10000, 50000)).thenReturn(10); //return this when method is called with these inputs.
        when(calc.decision(10)).thenReturn("REJECTED");

        // accept any input and return it dynamically with thenAnswer.
        // object passed into thenAnswer() is InvocationOnMock object. use it to get first argument and return it like the save method does.
        when(appRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        // Call the method being tested.
        CreditResponse response = service.submitApplication(request);

        // Check if method output response has the correct values.
        // Assertions. first param is message shown if it fails.
        assertEquals("Decision should be REJECTED","REJECTED", response.decision());
        assertEquals("Risk Score should be 10", response.riskScore(), 80);
        assertNotNull("DTO appID should be set", response.appID());

        // Verify - check methods were called on the mocks. Useful if bugs are introduced e.g. method call removed when modifying class.
        verify(calc).calculateRiskScore(10000, 50000);
        verify(calc).decision(10);
        verify(appRepo).save(any());
        verify((eventRepo)).save(any());

    }

    void shouldThrowErrorInvalidApplication(){

    }

    void shouldGetApplication(){

    }


}
