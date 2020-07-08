package com.smartfoxpro.snowflake.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Iehor Funtusov, created 08/07/2020 - 11:55 AM
 */

@Getter
@Setter
@ConfigurationProperties("storage")
public class StorageProperties {

    private String location = "upload-dir";
}
