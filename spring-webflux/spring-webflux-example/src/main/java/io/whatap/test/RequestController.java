package io.whatap.test;

import java.util.Objects;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping(value = "/api")
@RestController
public class RequestController {

    @GetMapping(value = "/request")
    public Mono<String> testRequest() {
        String callUrl = "https://www.google.com/";
//        String callUrl = "http://localhost:8080/api/users";
        Mono<String> response = WebClient.builder().build()
                .mutate()
                .baseUrl(callUrl)
                .build()
                .get()
                // .post()
                // .bodyValue(String.class)
                .retrieve()
                .onStatus(httpStatusCode -> !httpStatusCode.is2xxSuccessful(),
                        clientResponse -> clientResponse.createException().flatMap(
                                clientResponseException -> Mono.error(new RuntimeException("Spring Webflux Request Exception"))))
                .toEntity(String.class)
                .flatMap(stringResponseEntity -> Mono.just(Objects.requireNonNull(stringResponseEntity.getBody())))
                .onErrorResume(throwable -> Mono.just(throwable.getMessage()))
                .doFinally(Void -> {
                    System.out.println("The End");
                });
        return response.thenReturn("test");
    }

}