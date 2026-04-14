package com.example.credit_service.common.util;

import com.example.credit_service.common.exception.EventToStringException;
import com.example.credit_service.event.OutboxEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

// Class to wrap an ObjectMapper writeValueAsString method as it requires try/catch, to prevent having them everywhere.
@Service
public class JsonUtil {

    private final ObjectMapper mapper;

    public JsonUtil(ObjectMapper mapper){
        this.mapper = mapper;
    }

    // Use Object class as it could be any event DTO or OutboxEvent subclass.
    public String toJson(Object obj){
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new EventToStringException("Failed with event object ", e);
        }

    }
}
