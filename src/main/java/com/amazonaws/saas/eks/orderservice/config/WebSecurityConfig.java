package com.amazonaws.saas.eks.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebSecurityConfig {
    @Bean
    public CorsFilter corsFilter() {
        // Create CORS configuration source
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Create CORS config
        CorsConfiguration config = new CorsConfiguration();

        // Allow credentials (cookies, auth headers)
        config.setAllowCredentials(true);

        // Allow requests from any origin (be cautious)
        config.addAllowedOriginPattern("*");

        // Allow all headers
        config.addAllowedHeader("*");

        // Allow all HTTP methods
        config.addAllowedMethod("*");

        // Register CORS config for all endpoints
        source.registerCorsConfiguration("/**", config);

        // Return CORS filter
        return new CorsFilter(source);
    }
}
