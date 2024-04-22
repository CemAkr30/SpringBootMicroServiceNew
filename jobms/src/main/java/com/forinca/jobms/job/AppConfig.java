package com.forinca.jobms.job;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {


    @Bean
    @LoadBalanced // This annotation tells Spring Cloud to create a Ribbon-backed RestTemplate
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
