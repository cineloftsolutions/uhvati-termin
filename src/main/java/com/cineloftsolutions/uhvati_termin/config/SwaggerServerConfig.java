package com.cineloftsolutions.uhvati_termin.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerServerConfig {

    @Value("${server.url}") String serverUrl;
    @Value("${server.description}") String serverDescription;

    @Bean
    public OpenAPI customOpenAPI() {
        Server production = new Server();
        production.setUrl(serverUrl);
        production.setDescription(serverDescription);

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .servers(List.of(production))
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
