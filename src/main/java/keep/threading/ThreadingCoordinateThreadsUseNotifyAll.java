package keep.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadingCoordinateThreadsUseNotifyAll {
    static class RedisWorker implements Runnable {
        private ThreadingCoordinateThreadsUseNotifyAll cn;
        RedisWorker(ThreadingCoordinateThreadsUseNotifyAll cn) {
            this.cn = cn;
        }

        public void run() {
            try {
                synchronized (cn) {
                    cn.wait();
                }
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            System.out.println(Thread.currentThread().getName() + "\tRedis ready! I will do my work !");
        }
    }

    public static void main(String[] args) throws Exception {
        ThreadingCoordinateThreadsUseNotifyAll cn = new ThreadingCoordinateThreadsUseNotifyAll();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i=0; i<3; i++) {
            executorService.execute(new RedisWorker(cn));
        }
        executorService.shutdown();

        System.out.println("Check Redis availability now....");
        TimeUnit.SECONDS.sleep(3);  // Simulate the check process
        System.out.println("Redis online now, notify all threads now !");
        synchronized (cn) {
            cn.notifyAll();
        }

        executorService.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("Done");
    }
}
