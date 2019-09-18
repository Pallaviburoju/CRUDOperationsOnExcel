package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan("com.example.service")
@ComponentScan("com.example.pgrm")
@SpringBootApplication
public class ConvertXlToJsonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConvertXlToJsonApplication.class, args);
	}

}
