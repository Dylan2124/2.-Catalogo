package com.Catalogo2._Catalogo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "http://localhost:8082", description = "Servidor Local")})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
