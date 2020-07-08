package com.smartfoxpro.snowflake.service.impl;

import com.smartfoxpro.snowflake.entity.DBConnection;
import com.smartfoxpro.snowflake.repository.DBConnectionRepository;
import com.smartfoxpro.snowflake.service.DBConnectionService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Iehor Funtusov, created 08/07/2020 - 9:45 AM
 */

@Service
@AllArgsConstructor
public class DBConnectionServiceImpl implements DBConnectionService {

    private final DBConnectionRepository dbConnectionRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void createNewDBConnection(DBConnection connection) {
        dbConnectionRepository.save(connection);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void updateDBConnection(DBConnection connection) {
        dbConnectionRepository.save(connection);
    }

    @Override
    @Transactional(readOnly = true)
    public DBConnection findDBConnectionById(Long id) {
        return dbConnectionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid DBConnection Id:" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DBConnection> findAll() {
        return dbConnectionRepository.findAll();
    }
}
