package com.smartfoxpro.snowflake.exception;

/**
 * @author Iehor Funtusov, created 08/07/2020 - 11:59 AM
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
