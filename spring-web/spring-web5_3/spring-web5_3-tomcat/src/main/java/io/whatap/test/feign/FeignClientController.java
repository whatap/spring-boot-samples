package io.whatap.test.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class FeignClientController {
    @Autowired
    CustomFeignClient customFeignClient;

    // feign from
    @GetMapping(value = "/request/feign")
    public FeignResponse test(@RequestParam(value = "title") String title, @RequestParam(value = "message") String message) {
        return customFeignClient.getEchoMessage(title, message);
    }

    // feign to
    @GetMapping(value = "/to")
    public FeignResponse to(@RequestParam(value = "title") String title, @RequestParam(value = "message") String message) {
        FeignResponse feignResponse = new FeignResponse();
        feignResponse.setMessages(new FeignResponse.Messages(title, message));
        return feignResponse;
    }
}
