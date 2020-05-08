package keep.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ThreadingCoordinatorThreadsUseCondition {

    static class RedisWorker implements Runnable {
        private Condition condition;
        private Lock lock;

        RedisWorker(Condition condition, Lock lock) {
            this.condition = condition;
            this.lock = lock;
        }
        public void run() {
            this.lock.lock();
            try {
                this.condition.await();
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            } finally {
                this.lock.unlock();
            }
            System.out.println("Redis Ready! I can do my work now!");
        }
    }

    public static void main(String[] args) throws Exception {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i=0; i<3; i++) {
            executorService.execute(new RedisWorker(condition, lock));
        }
        executorService.shutdown();

        System.out.println("Check Redis availability now....");
        TimeUnit.SECONDS.sleep(3);  // Simulate the check process
        System.out.println("Redis online now, notify all threads now !");
        lock.lock();
        try {
            condition.signalAll();
        } finally {
            lock.unlock();
        }

        executorService.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("Done");
    }
}
