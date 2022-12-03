package com.example.criptografiaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication //(exclude = {SecurityAutoConfiguration.class})
public class CriptografiaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CriptografiaApiApplication.class, args);
	}
}
