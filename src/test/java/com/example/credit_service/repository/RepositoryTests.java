package com.example.credit_service.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

// Use Mockito in this test class to enable @Mock/@InjectMocks.
@ExtendWith(MockitoExtension.class)
public class RepositoryTests {

    //Test finding unprocessed events
    @Test
    void shouldFindByProcessedFalse(){

    }

    //Test when no results (Optional object should indicate empty).
    @Test
    void whenNoResultsFindByProcessedFalse(){

    }
}
