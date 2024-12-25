package io.pow.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class PowConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(PowConfigApplication.class, args);
	}

}