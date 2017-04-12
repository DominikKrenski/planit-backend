package com.dominik.backend.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


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
                .antMatchers("/console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/user/register", "/user/register/").permitAll()
                .antMatchers(HttpMethod.POST, "/user/login", "/user/login/").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
