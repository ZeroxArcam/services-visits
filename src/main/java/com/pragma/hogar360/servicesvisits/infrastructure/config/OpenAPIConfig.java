package com.pragma.hogar360.servicesvisits.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("Microservice Visits")
                        .version("1.0.0")
                        .description("This microservice manages the scheduling of property visits. It allows sellers to define their availability for viewings, and potentially allows buyers to request and schedule visits. The service will handle the association between available time slots, properties, and sellers, ensuring proper coordination for property viewings. Future enhancements may include notifications and more advanced scheduling features.")
                        .contact(new Contact()
                                .name("Ciro Alfonso Pallares Fragozo")
                        )
                        //.email("soporte@empresa.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://github.com/ZeroxArcam")))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName));


    }
}