package com.misight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ServerApplication.class, args);
		Environment env = context.getEnvironment();

		String port = env.getProperty("server.port", "8080"); // Default to 8080 if not set
		System.out.println("Application is running on http://localhost:" + port);
	}
}
