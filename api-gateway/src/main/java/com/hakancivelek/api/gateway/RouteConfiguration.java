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
                .route(r ->
                        r.path("/web/products/**")
                                .filters(f ->
                                        f.rewritePath("/web/?(?<segment>.*)", "/v1/$\\{segment}"))
                                .uri("http://product-service:8080")
                )
                .build();
    }
}
