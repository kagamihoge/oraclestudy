package oraclestudy.csv;

import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OutputCSV {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        final String br = System.getProperty("line.separator");
        final String comma = ",";
        
        final String sqlStr = "SELECT * FROM employees";
        try (Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@192.168.0.20:1521:XE", "kagamihoge", "xxxx");
            PreparedStatement sql = connection.prepareStatement(sqlStr);) {
            sql.setFetchSize(100);

            try (ResultSet r = sql.executeQuery();
                BufferedWriter writer = Files.newBufferedWriter(
                    Paths.get("employees.csv"), Charset.defaultCharset())) {
                
                while (r.next()) {
                    StringBuilder b = new StringBuilder();
                    b.append(r.getString("employee_id")).append(comma)
                        .append(r.getString("first_name")).append(comma)
                        .append(r.getString("last_name")).append(comma)
                        .append(r.getString("email")).append(comma)
                        .append(r.getString("phone_number")).append(comma)
                        .append(r.getString("hire_date")).append(comma)
                        .append(r.getString("job_id")).append(comma)
                        .append(r.getString("salary")).append(comma)
                        .append(r.getString("commission_pct")).append(comma)
                        .append(r.getString("manager_id")).append(comma)
                        .append(r.getString("department_id"))
                        .append(br);
                    writer.write(b.toString());
                }
            }
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
