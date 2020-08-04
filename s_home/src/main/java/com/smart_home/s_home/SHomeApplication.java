package com.smart_home.s_home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SHomeApplication.class, args);
	}
}
