package com.cineloftsolutions.uhvati_termin.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerServerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server localhost = new Server();
        localhost.setUrl("http://localhost:8080");
        localhost.setDescription("Local server");

        Server production = new Server();
        production.setUrl("https://uhvati-termin-production.up.railway.app");
        production.setDescription("Production server");

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .servers(List.of(localhost, production))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components().addSecuritySchemes(securitySchemeName,
                        new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ));
    }
}
