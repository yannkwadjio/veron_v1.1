package com.veron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Veron extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(Veron.class, args);
	}

}
