package com.example.credit_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration indications that this class should be managed by the Spring container; Spring will instantiate the class,
// execute it's @Bean methods. Registers returned objects in the ApplicationContext (spring container/map)
@Configuration
// Activate Spring Security support.
// Hooks into Servlet filter chain and enables HttpSecurity, turns on auth mechanisms.
// TODO: review this in more detail how this works.
@EnableWebSecurity
class SecurityConfig {
    //@Bean means the returned object from the method should be stored and managed as a bean.
    // Spring Security operates using filters. A filter will run before the controller and can block/modify/allow a request.
    // Only makes it to the controller if allowed.
    // This method is to define one of these filters.
    // DI again, Spring will look in the ApplicationContext when it tries to create the bean and provide a HttpSecurity
    // object automatically as it's a complex class that needs wiring. The class is a Builder for the SecurityFilterChain.
    @Bean
    SecurityFilterChain filter(HttpSecurity http){
        // Start configuration.
        http.csrf(crsf -> crsf.disable()) // Cross-site Request Forgery protection. disable as it's a REST API not using sessions or cookeis.
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated()) //Every HTTP request must be authenticated. No anonymous access.
            .httpBasic(Customizer.withDefaults()); // Basic authentication; username and password. Spring to decode and validate + allow/deny.

        return http.build(); // build the SecurityFilterChain configured in the method. Spring will register it and apply it to all incoming requests.
    }
}
