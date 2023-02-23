package com.jb.msservicea.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {

    @Bean
    @LoadBalanced
    /* @LoadBalanced knows how to work with products that have the dependency of "consul" (hashicorp)
        * and it does the "round-robin between the available instances"
        * and that means that every restTemplate quest will be direct to another instance
        * it's called -> "Client Side Discovery",
        * and it's good for 15 to 50 micro-services apps.
        * above that you'll need some other orchestrator, maybe "Kubernetes" or others
        * */
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
