package com.smartfoxpro.snowflake.repository;

import com.smartfoxpro.snowflake.entity.DBConnection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Iehor Funtusov, created 08/07/2020 - 9:41 AM
 */

@Repository
public interface DBConnectionRepository extends JpaRepository<DBConnection, Long> { }
