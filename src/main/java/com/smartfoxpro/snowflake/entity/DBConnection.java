package com.smartfoxpro.snowflake.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Iehor Funtusov, created 08/07/2020 - 9:17 AM
 */

@Getter
@Setter
@Entity
public class DBConnection implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String user;
    private String password;
    private String warehouse;
    private String db;
    private String schema;
    private String region;
}
