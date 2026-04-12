package com.example.credit_service.event;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EventRouter {

    // all event handlers stored in a map in the constructor. key is the event type
    // e.g. string NEW_APPLICATION, COMPLETE_APPLICATION, RISK etc.
    private final Map<String, EventHandler> handlers;

    // Spring Boot automatically constructs the list and injects (DI) all @components classes implementing EventHandler
    public EventRouter(List<EventHandler> list){
        // use stream to convert list to map i.e process each element.
        // collect takes all elements
        // Collectors.toMap() is a helper method to convert to maps.
        // First parameter is the key, second is the value. Use method reference :: to easily get the eventType for key. Lambda function to use the handler itself as the value rather than apply specific function.
        this.handlers = list.stream().collect(Collectors.toMap(EventHandler::getEventType, h -> h));
    }

    // Message is serialized into event object in consumer.
    public void route(OutboxEvent event) {

        // get matching handler using map key.
        EventHandler handler = handlers.get(event.getEventType());

        // Throw exception if message receieved has event type that does not have a handler.
        if (handler == null) {
            throw new IllegalArgumentException("No Handler exists for event type: " + event.getEventType());
        }

        // Event is routed to the correct handler.
        handler.handle(event);

    }
}
