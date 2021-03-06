package oraclestudy.librarycachemiss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LibraryAllHit {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        final String sqlStr = "SELECT column_pk_int FROM million_rows_table WHERE column_pk_int = ?";
        try (Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@192.168.0.20:1521:XE", "kagamihoge", "xxxx");
            PreparedStatement sql = connection.prepareStatement(sqlStr);) {
            
            int j=0;
            for (int i=1; i<=1_000_000; i++) {
                sql.setInt(1, i);
                try (ResultSet r = sql.executeQuery();) {
                    while (r.next()) {
                        int pkValue = r.getInt("column_pk_int");
                        j++;
                    }
                }
            }
            System.out.println(j);
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
