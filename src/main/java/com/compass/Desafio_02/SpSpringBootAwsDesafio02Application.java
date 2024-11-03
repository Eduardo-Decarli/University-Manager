package com.compass.Desafio_02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpSpringBootAwsDesafio02Application {

	public static void main(String[] args) {
		SpringApplication.run(SpSpringBootAwsDesafio02Application.class, args);
	}

}
