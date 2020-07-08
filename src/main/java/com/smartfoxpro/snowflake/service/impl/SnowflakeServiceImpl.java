package com.smartfoxpro.snowflake.service.impl;

import com.smartfoxpro.snowflake.data.SnowflakeTermsData;
import com.smartfoxpro.snowflake.entity.DBConnection;
import com.smartfoxpro.snowflake.repository.DBConnectionRepository;
import com.smartfoxpro.snowflake.service.SnowflakeService;
import com.smartfoxpro.snowflake.service.StorageFileService;

import com.smartfoxpro.snowflake.util.QueryUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;

/**
 * @author Iehor Funtusov, created 08/07/2020 - 10:57 AM
 */

@Service
@AllArgsConstructor
public class SnowflakeServiceImpl implements SnowflakeService {

    private final DBConnectionRepository dbConnectionRepository;
    private final StorageFileService storageFileService;

    @Override
    public boolean testConnect(Long id) {
        DBConnection dbConnection = dbConnectionRepository.findById(id).get();
        System.out.println("Create JDBC connection");
        Connection connection;
        try {
            connection = getConnection(dbConnection);
            if (connection == null) {
                return false;
            }
            System.out.println("Done creating JDBC connectionn");
            // create statement
            System.out.println("Create JDBC statement");
            Statement statement = connection.createStatement();
            System.out.println("Done creating JDBC statementn");
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean runSnowflakeTerms(Long id, SnowflakeTermsData data) {
        Path path = storageFileService.load(data.getFilename());
        try {
            String readFile = Files.readString(path);
            String columnRead = readFile.substring(0, readFile.indexOf("2016"));
            String valueRead = readFile.substring(readFile.indexOf("2016"), readFile.length());
            String[] values = valueRead.split("\n");

            DBConnection dbConnection = dbConnectionRepository.findById(id).orElse(null);
            Map<Integer, List<String>> queryMap = QueryUtil.rowsPreparation(values, data.getRows());
            System.out.println("queryMap = " + queryMap.keySet().size());

            if (dbConnection != null) {
                Connection connection;
                try {
                    connection = getConnection(dbConnection);
                    if (connection == null) {
                        return false;
                    }
                    Statement statement = connection.createStatement();
                    StringBuilder insertQuery = new StringBuilder();
                    String basicQuery = "INSERT INTO " + data.getTableName() +" ("+ columnRead + ")" + " VALUES ";
                    for (Integer integer : queryMap.keySet()) {
                        System.out.println("integer = " + integer);
                        insertQuery.append(basicQuery);
//                        List<String> queryList = queryMap.get(integer);
                        List<String> queryList = Arrays.asList(
                                QueryUtil.requestPreparation(values[0]),
                                QueryUtil.requestPreparation(values[1]),
                                QueryUtil.requestPreparation(values[2]),
                                QueryUtil.requestPreparation(values[3]),
                                QueryUtil.requestPreparation(values[4])
                                );
                        System.out.println("queryList = " + queryList.size());
                        queryList.forEach(q -> System.out.println("query q = " + q));

                        for (String query : queryList) {
                            System.out.println("query = " + query);
                            insertQuery.append("(").append(query).append("),");
                        }
                        String resultQuery = insertQuery.toString();
                        resultQuery = resultQuery.substring(0, resultQuery.length() - 1);
                        System.out.println("resultQuery = " + resultQuery);
                        statement.executeUpdate(resultQuery);
                        if (integer == 0) {
                            break;
                        }

//                        insertQuery = new StringBuilder();
                    }

                    statement.close();
                    return true;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Connection getConnection(DBConnection connection) throws SQLException {
        try {
            Class.forName("com.snowflake.client.jdbc.SnowflakeDriver");
        } catch (ClassNotFoundException ex) {
            System.err.println("Driver not found");
        }

        Properties properties = new Properties();
        properties.put("user", connection.getUser());
        properties.put("password", connection.getPassword());
        properties.put("warehouse", connection.getWarehouse());
        properties.put("db", connection.getDb());
        properties.put("schema", connection.getSchema());

        String connectStr = "jdbc:snowflake://" + connection.getRegion() + ".snowflakecomputing.com";

        return DriverManager.getConnection(connectStr, properties);
    }
}
