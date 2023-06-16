package io.whatap.test;

import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

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

    @GetMapping(value = "/request/hc5/get")
    public String testHc5Get() {
        String resultContent = null;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            ClassicHttpRequest httpGet = ClassicRequestBuilder.get("http://httpbin.org/get")
                    .build();
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST call CloseableHttpResponse#close() from a finally clause.
            // Please note that if response content is not fully consumed the underlying
            // connection cannot be safely re-used and will be shut down and discarded
            // by the connection manager.
            resultContent = httpclient.execute(httpGet, response -> {
                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                final HttpEntity entity1 = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity1.getContent()));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                EntityUtils.consume(entity1);
                return sb.toString();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultContent;
    }

    @GetMapping(value = "/request/hc5/post")
    public String testHc5Post() {
        String resultContent = null;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            ClassicHttpRequest httpPost = ClassicRequestBuilder.post("http://httpbin.org/post")
                    .setEntity(new UrlEncodedFormEntity(Arrays.asList(
                            new BasicNameValuePair("username", "vip"),
                            new BasicNameValuePair("password", "secret"))))
                    .build();
            resultContent = httpclient.execute(httpPost, response -> {
                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                final HttpEntity entity2 = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity2.getContent()));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                EntityUtils.consume(entity2);
                return sb.toString();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultContent;
    }

}
