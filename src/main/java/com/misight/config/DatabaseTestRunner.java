package com.misight.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseTestRunner {

    @Bean
    CommandLineRunner testDatabaseConnection(JdbcTemplate jdbcTemplate) {
        return args -> {
            try {
                Integer count = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
                if (count != null && count == 1) {
                    System.out.println("Database connection is successful!");
                } else {
                    System.out.println("Database test query failed.");
                }
            } catch (Exception e) {
                System.err.println("Database connection error: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}