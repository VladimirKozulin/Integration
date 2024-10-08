package com.example.service.integration_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class IntegrationAppApplication { //Здесь мы интегрируемся с нашим приложением

	public static void main(String[] args) {
		SpringApplication.run(IntegrationAppApplication.class, args);
	}

}
