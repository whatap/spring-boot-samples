package io.whatap.test.okhttp3;

import okhttp3.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api")
public class OkHttp3Controller {

    @GetMapping(value = "/request/ok3")
    public String test() {
        final String url = "https://google.com";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/request/ok3/parameter")
    public String test2() {
        final String uri = "http://localhost:8081";

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(uri + "/api/request/feign")).newBuilder();
        urlBuilder.addQueryParameter("title", "test-title");
        urlBuilder.addQueryParameter("message", "test-message");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
