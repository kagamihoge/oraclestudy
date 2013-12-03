package oraclestudy.updatefromfile;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

public class UpdateThroughTemporaryFromFile {

    public static void main(String[] args) throws Exception {
        final int IND_ID = 0;
        final int IND_VALUE = 1;
        final int PARAM_IND_ID= 1;
        final int PARAM_IND_VALUE = 2;
        
        long start = System.currentTimeMillis();

        List<String> lines = Files.readAllLines(Paths.get("input.csv"), Charset.defaultCharset());

        final String insertIntoTempStr = "INSERT INTO temp_update_table(ID, VALUE) VALUES (?, ?)";
        final String updateFromTempStr = "MERGE INTO UPDATE_TABLE u USING (SELECT ID temp_id, VALUE temp_value FROM temp_update_table) t ON (u.ID = t.temp_id) WHEN MATCHED THEN UPDATE SET u.VALUE = t.temp_value";
        try (Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@192.168.0.20:1521:XE", "kagamihoge", "xxxx");
            PreparedStatement sql = connection.prepareStatement(insertIntoTempStr);
            PreparedStatement updateSql = connection.prepareStatement(updateFromTempStr);) {
            connection.setAutoCommit(false);

            for (String idAndValue : lines) {
                String[] split = idAndValue.split(",");

                sql.setInt(PARAM_IND_ID, Integer.parseInt(split[IND_ID]));
                sql.setString(PARAM_IND_VALUE, split[IND_VALUE]);

                sql.executeUpdate();
            }
            updateSql.executeUpdate();
            connection.commit();
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
