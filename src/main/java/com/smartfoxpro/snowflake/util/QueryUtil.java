package com.smartfoxpro.snowflake.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Iehor Funtusov, created 08/07/2020 - 4:08 PM
 */

@UtilityClass
public class QueryUtil {

    public Map<Integer, List<String>> rowsPreparation(String[] values, int rows) {
        System.out.println("values = " + values.length);
        System.out.println("rows = " + rows);
        Map<Integer, List<String>> map = new HashMap<>();
        Integer position = 0;
        List<String> strings = new ArrayList<>();
        for (int i = 0 ; i < values.length ; i++) {
            if (i % rows == 0) {
                map.put(position, strings);
                position++;
                strings = new ArrayList<>();
            } else {
                strings.add(requestPreparation(values[i]));
            }
        }
        return map;
    }

    public String requestPreparation(String oldQuery) {
        oldQuery = oldQuery.replaceAll("\"", "'");
        StringBuilder resultQuery = new StringBuilder();
        String[] result = oldQuery.split("\\s*,[,\\s]*");
        for (int i = 0 ; i < result.length ; i++) {
            if (result[i].startsWith("'") && result[i].length() > 2) {
                resultQuery.append(result[i]).append(", ").append(result[i + 1]);
                i++;
            } else if (result[i].startsWith("'")) {
                resultQuery.append(result[i]);
            } else {
                resultQuery.append("'").append(result[i]).append("'");
            }
            if (i < result.length - 1) {
                resultQuery.append(",");
            }
        }
        return resultQuery.toString();
    }
}
