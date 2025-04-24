package com.cineloftsolutions.uhvati_termin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSFilter {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                System.out.println("Opening CORS configurer..");
                registry
                        .addMapping("/**")
                        .allowedMethods("HEAD", "GET", "POST", "PUT");
            }
        };
    }
}
