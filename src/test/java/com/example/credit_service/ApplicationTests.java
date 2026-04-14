package com.example.credit_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.kafka.consumer.group-id=test-group",
        "spring.kafka.listener.auto-startup=false"
}) // stop kafka errors when compiling and testing TODO: fix it.
class ApplicationTests {

    @Test
    void contextLoads() {
    }

}
