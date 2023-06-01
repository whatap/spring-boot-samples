package io.whatap.test.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "test-feign", url = "http://localhost:8080")
public interface CustomFeignClient {

    @GetMapping(path = "/api/to", consumes = "application/json")
    FeignResponse getEchoMessage(@RequestParam(value = "title") String title, @RequestParam(value = "message") String message);
}
