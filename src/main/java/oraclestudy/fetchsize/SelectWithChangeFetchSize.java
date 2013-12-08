package oraclestudy.fetchsize;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SelectWithChangeFetchSize {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        final String sqlStr = "SELECT column1 FROM million_rows_table";
        try (Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@192.168.0.20:1521:XE", "kagamihoge", "xxxx");
            PreparedStatement sql = connection.prepareStatement(sqlStr);) {
            sql.setFetchSize(100);
            
            try (ResultSet r = sql.executeQuery();) {
                int i=0;
                while (r.next()) {
                    String s1 = r.getString("column1");
                    i++;
                }
                System.out.println(i);
            }
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
