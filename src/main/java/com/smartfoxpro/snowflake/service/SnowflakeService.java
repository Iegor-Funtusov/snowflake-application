package com.smartfoxpro.snowflake.service;

import com.smartfoxpro.snowflake.data.SnowflakeTermsData;

/**
 * @author Iehor Funtusov, created 08/07/2020 - 10:56 AM
 */
public interface SnowflakeService {

    boolean testConnect(Long id);
    boolean runSnowflakeTerms(Long id, SnowflakeTermsData data);
}
