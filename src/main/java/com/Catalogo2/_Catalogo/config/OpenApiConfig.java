package com.Catalogo2._Catalogo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI catalogoOpenAPI() {
        // El nombre exacto de la cabecera que exige tu SecurityFilter
        final String nombreCabecera = "Autorizado";

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

                //Forzamos la ruta relativa "/" como primera opción.
                // Esto hace que Swagger use el puerto desde donde abres la página (el 8080 del Gateway)
                .servers(List.of(
                        new Server().url("/").description("Ruta Relativa Automática (API Gateway)"),
                        new Server().url("http://localhost:8080").description("Servidor local (Gateway)"),
                        new Server().url("http://localhost:8082").description("Directo al Microservicio Catálogo")
                ))

                // Activamos el candado de seguridad en la interfaz gráfica
                .addSecurityItem(new SecurityRequirement().addList(nombreCabecera))

                //Le enseñamos a Swagger que "Autorizado" es una API KEY que viaja en el HEADER
                .components(new Components()
                        .addSecuritySchemes(nombreCabecera,
                                new SecurityScheme()
                                        .name(nombreCabecera)
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                        )
                );
    }
}