package com.coztion.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.flyway.autoconfigure.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Value("${spring.flyway.clean-on-error}")
    private boolean cleanOnError;

    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        return flyway -> {
            try {
                flyway.migrate();
            } catch (Exception e) {
                if (cleanOnError) {
                    flyway.clean();
                    flyway.migrate();
                }
            }
        };
    }
}
