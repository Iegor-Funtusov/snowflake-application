package com.smartfoxpro.snowflake.exception;

/**
 * @author Iehor Funtusov, created 08/07/2020 - 12:01 PM
 */

public class StorageFileNotFoundException extends StorageException {

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

