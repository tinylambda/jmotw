package keep.concurrency;

import sun.applet.Main;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrencyCountDownLatch {
    static class CheckTask implements Runnable {
        private String what;
        private CountDownLatch countDownLatch;
        public CheckTask(String what, CountDownLatch countDownLatch) {
            this.what = what;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println("Checking " + this.what);
            Random random = new Random();
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(5));
            } catch (InterruptedException e) {
                System.out.println("Worker interrupted!");
            }
            System.out.println("Done for checking " + this.what + "!");
            this.countDownLatch.countDown();
        }
    }

    static class MainTask implements Runnable {
        private String what;
        private CountDownLatch countDownLatch;
        public MainTask(String what, CountDownLatch countDownLatch) {
            this.what = what;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println("Waiting for checking tasks");
            try {
                this.countDownLatch.await();
            } catch (InterruptedException e) {
                System.out.println("MainTask interrupted!");
            }
            System.out.println("All check tasks done, main task go on: " + this.what);
        }
    }

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(3);

        executorService.execute(new CheckTask("Redis", countDownLatch));
        executorService.execute(new CheckTask("MySQL", countDownLatch));
        executorService.execute(new CheckTask("ES", countDownLatch));
        executorService.execute(new MainTask("Feeding data to Redis", countDownLatch));
        executorService.execute(new MainTask("Feeding data to MySQL", countDownLatch));
        executorService.execute(new MainTask("Feeding data to ES", countDownLatch));
        executorService.shutdown();
        executorService.awaitTermination(3, TimeUnit.SECONDS);
    }
}
