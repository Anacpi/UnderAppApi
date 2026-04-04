package com.tcc.underapp_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Underapp API Spring Boot application.
 * Responsible for bootstrapping the application context.
 */
@SpringBootApplication
public class UnderappApiApplication {

	/**
	 * Starts the Spring Boot application.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(UnderappApiApplication.class, args);
	}

}
