package com.smartfoxpro.snowflake.data;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Iehor Funtusov, created 08/07/2020 - 9:43 AM
 */

@Getter
@Setter
public class SnowflakeTermsData {

    private String filename;
    private Integer rows;
    private String tableName;
}
