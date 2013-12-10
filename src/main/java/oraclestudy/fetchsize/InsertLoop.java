package oraclestudy.fetchsize;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.commons.lang3.RandomStringUtils;

public class InsertLoop {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        final String insertStr = "INSERT INTO insert_to_million_rows_table VALUES (?)";
        try (Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@192.168.0.20:1521:XE", "kagamihoge", "xxxx");
            PreparedStatement sql = connection.prepareStatement(insertStr);) {
            connection.setAutoCommit(false);
            
            for (int i=0; i<1_000_000; i++) {
                sql.setString(1, RandomStringUtils.randomAlphanumeric(20));
                sql.executeUpdate();
            }
            
            connection.commit();
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
