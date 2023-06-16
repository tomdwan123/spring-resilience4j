package com.phucdevs.ServiceA.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
@RequestMapping("/a")
public class ServiceAController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8081/";

    private static final String SERVICE_A = "serviceA";
    int count = 1;

    @GetMapping
    //@CircuitBreaker(name = SERVICE_A, fallbackMethod = "getServiceAFallback")
    //@Retry(name = SERVICE_A)
    @RateLimiter(name = SERVICE_A)
    public String getServiceA() {
        System.out.println("Retry method called " + count++ + " times at " + new Date());

        String url = BASE_URL + "b";
        return restTemplate.getForObject(
                url,
                String.class
        );
    }

    public String getServiceAFallback(Exception e) {
        return "This is a fallback method for ServiceA";
    }
}
