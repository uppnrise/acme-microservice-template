package com.upp.microservices.composite.product;

import com.upp.microservices.composite.product.services.ProductCompositeIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

@SpringBootApplication
@ComponentScan("com.upp")
public class ProductCompositeServiceApplication {

    @Autowired
    HealthAggregator healthAggregator;

    @Autowired
    ProductCompositeIntegration integration;

    @Bean
    ReactiveHealthIndicator coreServices() {
        ReactiveHealthIndicatorRegistry registry = new DefaultReactiveHealthIndicatorRegistry(new LinkedHashMap<>());

        registry.register("product", () -> integration.getProductHealth());
        registry.register("recommendation", () -> integration.getRecommendationHealth());
        registry.register("review", () -> integration.getReviewHealth());

        return new CompositeReactiveHealthIndicator(healthAggregator, registry);
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductCompositeServiceApplication.class, args);
    }

}
