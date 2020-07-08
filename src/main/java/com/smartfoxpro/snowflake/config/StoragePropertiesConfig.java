package com.smartfoxpro.snowflake.config;

import com.smartfoxpro.snowflake.service.StorageFileService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Iehor Funtusov, created 08/07/2020 - 11:55 AM
 */

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StoragePropertiesConfig {

    @Bean
    CommandLineRunner init(StorageFileService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
