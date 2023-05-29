package io.whatap.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("test_route",
                        r -> r.path("")
                                .filters(f -> f.addRequestHeader("Hello", "World"))
                                .uri("https://www.google.com"))
                .route("redirect", r -> r.path("/redirect/**")
                        .filters(f -> f.addRequestHeader("Hello", "World")
                                .rewritePath("/redirect/*", "/api/response"))
                        .uri("http://localhost:8080"))
                .build();
    }
}
