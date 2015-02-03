package oraclestudy.multithreadupdate;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Main {

    public static void main(String[] args) {
        final int UPDATE_SIZE = 100_000;
        final int THREAD_SIZE = 200;
        final int UPDATE_PER_THREAD_SIZE = UPDATE_SIZE / THREAD_SIZE;
        
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_SIZE);
        final long start = System.currentTimeMillis();
        
        CyclicBarrier barrier = new CyclicBarrier(THREAD_SIZE, new Runnable() {
            public void run() {
                long end = System.currentTimeMillis();
                System.out.println("time:" + (end - start));
            }
        });
        
        for (int i = 0; i < THREAD_SIZE; ++i) {
            int startNum = i * UPDATE_PER_THREAD_SIZE + 1;
            threadPool.submit(new UpdateThread(startNum, UPDATE_PER_THREAD_SIZE, barrier));
        }
    }
}
