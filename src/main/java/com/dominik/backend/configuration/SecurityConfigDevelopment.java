package com.dominik.backend.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Created by dominik on 06.04.17.
 */

@Configuration
@Profile("development")
public class SecurityConfigDevelopment extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(SecurityConfigDevelopment.class);

    @Override
    public void configure(WebSecurity web) throws Exception {

        logger.info("UDOSTĘPNIENIE SWAGGER 2");

        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration-security",
                "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/security",
                "/swagger-resources/configuration/ui");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        logger.info("WYWOŁANIE KONFIGURACJI BEZPIECZEŃSTWA");

        http
                .authorizeRequests()
                .antMatchers("/console").permitAll()
                .anyRequest()
                .authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
