package com.odp.opendataplatform.spark.validator;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonValidator {
    // Regular expression pattern for a valid email address
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@(.+)$";

    public Map<String, List<Integer>> validateEmailColumn(Dataset<Row> data, String columnName) {
        Map<String, List<Integer>> errors = new HashMap<>();

        data.foreach(row -> {
            String email = row.getAs(columnName);
            if (email != null && !email.matches(EMAIL_REGEX)) {
                int rowNum = row.fieldIndex(columnName);
                String errorReason = "Invalid email format";

                // Get the list of row numbers for the error reason, or create a new list
                List<Integer> errorList = errors.getOrDefault(errorReason, new ArrayList<>());
                errorList.add(rowNum);

                // Update the map with the error reason and the list of row numbers
                errors.put(errorReason, errorList);
            }
        });

        return errors;
    }
}
