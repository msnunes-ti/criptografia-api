package com.example.criptografiaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //(exclude = {SecurityAutoConfiguration.class})
public class CriptografiaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CriptografiaApiApplication.class, args);
	}
}
