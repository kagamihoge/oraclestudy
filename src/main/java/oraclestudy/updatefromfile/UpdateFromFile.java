package oraclestudy.updatefromfile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UpdateFromFile {
    
    public static void main(String[] args) throws IOException, SQLException {
        final int IND_ID = 0;
        final int IND_VALUE = 1;
        final int PARAM_IND_ID= 2;
        final int PARAM_IND_VALUE = 1;
        
        long start = System.currentTimeMillis();

        List<String> lines = Files.readAllLines(Paths.get("input.csv"), Charset.defaultCharset());

        final String updateStr = "UPDATE update_table SET VALUE = ? WHERE ID = ?";
        try (Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@192.168.0.20:1521:XE", "kagamihoge", "xxxx");
            PreparedStatement sql = connection.prepareStatement(updateStr);) {
            connection.setAutoCommit(false);
            
            for (String idAndValue : lines) {
                String[] split = idAndValue.split(",");
                
                sql.setString(PARAM_IND_VALUE, split[IND_VALUE]);
                sql.setInt(PARAM_IND_ID, Integer.parseInt(split[IND_ID]));
                
                sql.executeUpdate();
            }
            
            connection.commit();
        }
        
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
