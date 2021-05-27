package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SamlServiceProviderApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext=	SpringApplication.run(SamlServiceProviderApplication.class, args);
		
		
	}

}
