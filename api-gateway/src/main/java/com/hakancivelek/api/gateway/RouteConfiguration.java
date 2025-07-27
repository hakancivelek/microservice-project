package com.hakancivelek.api.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // Product Service Route
                .route(r ->
                        r.path("/web/products/**")
                                .filters(f ->
                                        f.rewritePath("/web/?(?<segment>.*)", "/v1/$\\{segment}")
                                                .removeResponseHeader("Date"))
                                .uri("http://PRODUCT-SERVICE:8080")     // For api gateway
//                                .uri("http://product-service:8080")   // For docker-compose
                )

                // User Service Route
                .route(r ->
                        r.path("/api/users/**")
                                .filters(f ->
                                        f.removeResponseHeader("Date"))
                                .uri("http://USER-SERVICE:8080")   // For api gateway
//                                .uri("http://user-service:8080") // For docker-compose
                )

                // Order Service Route
                .route(r ->
                        r.path("/v1/orders/**")
                                .uri("http://ORDER-SERVICE:8080")   // For api gateway
//                                .uri("http://order-service:8080") // For docker-compose
                )
                .build();
    }
}
