package com.tmsps.ne4SpringBoot.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.tmsps.ne4spring.base,com.tmsps.ne4SpringBoot.action,com.tmsps.ne4SpringBoot.service")
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
