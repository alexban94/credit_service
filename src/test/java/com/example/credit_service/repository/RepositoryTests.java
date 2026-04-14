package com.example.credit_service.repository;

import com.example.credit_service.common.event.EventType;
import com.example.credit_service.common.event.RiskEvent;
import com.example.credit_service.event.EventRepo;
import com.example.credit_service.services.risk.model.RiskOutbox;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

//@ExtendWith(MockitoExtension.class)
@DataMongoTest
public class RepositoryTests {
    /* TODO
    @Autowired //inject it automatically.
    private EventRepo repo;


    //Test finding unprocessed events
    @Test
    void shouldFindByProcessedFalse(){



        RiskEvent riskEvent = new RiskEvent("123" , 80);

        RiskOutbox outbox = new RiskOutbox(UUID.randomUUID(), EventType.RISK.name(),
                riskEvent.toString(),"123" , Instant.now(), false);

        repo.save(outbox);

        List found = repo.findByProcessedFalse();

    }

    //Test when no results (Optional object should indicate empty).
    @Test
    void whenNoResultsFindByProcessedFalse(){
    }*/


}
