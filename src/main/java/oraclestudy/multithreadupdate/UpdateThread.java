package oraclestudy.multithreadupdate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class UpdateThread implements Runnable {
    
    private int start;
    private int size;
    private CyclicBarrier barrier;

    public UpdateThread(int start, int size, CyclicBarrier barrier) {
        this.start = start;
        this.size = size;
        this.barrier = barrier;
    }
    
    @Override
    public void run() {
        final String sqlStr = "UPDATE MUTLITHREAD_UPDATE_EXPR SET VALUE_COLUMN = '22222222223333333333' WHERE KEY_COLUMN = ?";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@//192.168.0.20:1521/ORCL.kagamihogex61", "kagamihoge", "a");
                PreparedStatement sql = connection.prepareStatement(sqlStr);) {
            connection.setAutoCommit(false);
            
            final int n = start + size;
            for (int i = start; i < n; i++) {
                sql.setInt(1, i);
                sql.executeUpdate();
            }
            
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

}
