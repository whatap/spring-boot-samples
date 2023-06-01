package io.whatap.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringWeb5Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringWeb5Application.class, args);
	}

}
