package keep.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrencyExecutorsShutdown {
    static class Worker implements Runnable {
        private int workerId;
        public Worker(int i) {
            this.workerId = i;
        }
        @Override
        public void run() {
            try{
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("exception!");
            }

            System.out.println("Worker: " + workerId);
        }
    }
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i=0; i<10; i++) {
            executorService.execute(new Worker(i));
        }
        System.out.println("before shutdown"); // Will appear before tasks completes
        // The call to shutdown() prevents new tasks from being submitted to the Executor
        // The main thread will continue to run all tasks submitted before shutdown() was
        // called. The program will exit as soon as all the tasks in the Executor finish.
        // This method does not wait for previously submitted tasks to complete execution.
        executorService.shutdown();
        System.out.println("after shutdown"); // Will appear before tasks completes

        executorService.awaitTermination(2, TimeUnit.SECONDS); // wait all tasks to complete
        System.out.println("after all tasks done !"); // Will appear after tasks complete
    }
}
