package com.softwarearchitecture.QuickBook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.softwarearchitecture.QuickBook")
public class QuickBookApplication {
	public static void main(String[] args) {
		SpringApplication.run(QuickBookApplication.class, args);
	}
}
	