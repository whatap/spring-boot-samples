package io.whatap.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RequestMapping(value = "/api")
@RestController
public class DemoController {

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

    @GetMapping(value = "/response")
    public Mono<Object> testResponse() {
        return Mono.just(new ResponseData("response data from webflux-netty-example"));
    }

}

class ResponseData {
    private String responseData;

    public ResponseData() {
    }

    public ResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }
}