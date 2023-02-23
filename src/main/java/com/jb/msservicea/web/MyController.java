package com.jb.msservicea.web;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("service")
public class MyController {

    private final RestTemplate restTemplate;

    @Value("${service.b.url}")
    private String bUrl;


    private static final String A_INFO = "aInformation";
    private int count = 1;


    @GetMapping("a")
    // Resilience4j annotations (3):
//    @Retry(name = A_INFO, fallbackMethod = "fallbackRetry")
//    @RateLimiter(name = A_INFO, fallbackMethod = "fallbackRateLimiter")
    @CircuitBreaker(name = A_INFO, fallbackMethod = "fallbackCircuitBreaker")

    public String whoAmI() {
//        if you choose @Retry - release the next line:
//        System.out.println("Retry method called " + count++ + " times at " + new Date());
        String bStr = restTemplate.getForObject(bUrl, String.class);
        return "Hello from ms-service-a ! calling for: " + bStr;
    }

    public String fallbackCircuitBreaker(Exception e) {
        return "(THIS IS FALLBACK) form service a";
    }

    public String fallbackRetry(Exception e) {
        return "(THIS IS RETRY FALLBACK) form service a";
    }

    public String fallbackRateLimiter(Exception e) {
        return "(THIS IS RATE LIMITER FALLBACK) form service a";
    }

}
