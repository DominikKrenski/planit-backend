package com.dominik.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by dominik on 06.04.17.
 */

@Configuration
@Profile("development")
public class CorsConfigDevelopment extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedMethods("PUT", "POST", "GET", "DELETE", "OPTIONS")
                .allowedOrigins("http://localhost:9000")
                .maxAge(3600);
    }
}
