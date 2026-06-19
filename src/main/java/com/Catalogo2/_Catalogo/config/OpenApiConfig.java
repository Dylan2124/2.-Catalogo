package com.Catalogo2._Catalogo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI catalogoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Productos - Catalogo")
                        .version("2.0.0")
                        .description("Microservicio para gestionar productos y sus especificaciones")
                        .contact(new Contact()
                                .name("Dylan fernandez")
                                .email("dy.fernandezl@duocuc.cl")
                                .url("https://github.com/Dylan2124/2.-Catalogo.git"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Servidor local"),
                        new Server().url("http://localhost:8081").description("Microservicio de Catalogo - compras")
                ));
    }
}
