package com.dominik.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by dominik on 06.04.17.
 */

@Configuration
@EnableJpaRepositories(basePackages = "com.dominik.backend.repository")
@EnableTransactionManagement
public class JpaConfig {
}
