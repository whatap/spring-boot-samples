package io.whatap.test;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class DemoController {

    @GetMapping(value = "/request/rt")
    public String test() {
        // RestTemplate + HttpURLConnection
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://google.com", String.class);
        String responseString = responseEntity.getBody();
        return responseString;
    }

    @GetMapping(value = "/request/rt/httpclient5")
    public String test2() {
        final String uri = "https://google.com";
        // RestTemplate + HttpClient5
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        String result = restTemplate.getForEntity(uri, String.class).getBody();
        return result;
    }

}
