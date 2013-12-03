package oraclestudy.updatefromfile;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

public class UpdateWithCaseFromFile {

    public static void main(String[] args) throws Exception {
        final int IND_ID = 0;
        final int IND_VALUE = 1;
        
        long start = System.currentTimeMillis();

        List<String> lines = Files.readAllLines(Paths.get("input.csv"), Charset.defaultCharset());
        
        final String updateStr = "UPDATE UPDATE_TABLE SET VALUE = CASE WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? WHEN ID = ? THEN ? ELSE VALUE END WHERE ID IN (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@192.168.0.20:1521:XE", "kagamihoge", "xxxx");
            PreparedStatement sql = connection.prepareStatement(updateStr);) {
            connection.setAutoCommit(false);

            int count = 0;
            for (String idAndValue : lines) {
                String[] split = idAndValue.split(",");

                count++;
                sql.setInt((count*2)-1, Integer.parseInt(split[IND_ID]));
                sql.setString(count*2, split[IND_VALUE]);
                sql.setInt(count + 100, Integer.parseInt(split[IND_ID]));
                
                if (count == 50) {
                    sql.executeUpdate();
                    count = 0;
                }
            }
            connection.commit();
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
