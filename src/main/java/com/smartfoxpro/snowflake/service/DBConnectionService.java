package com.smartfoxpro.snowflake.service;

import com.smartfoxpro.snowflake.entity.DBConnection;

import java.util.List;

/**
 * @author Iehor Funtusov, created 08/07/2020 - 9:42 AM
 */
public interface DBConnectionService {

    void createNewDBConnection(DBConnection connection);
    void updateDBConnection(DBConnection connection);
    DBConnection findDBConnectionById(Long id);
    List<DBConnection> findAll();
}
