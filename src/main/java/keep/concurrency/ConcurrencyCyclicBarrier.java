package keep.concurrency;

import java.util.Random;
import java.util.concurrent.*;

public class ConcurrencyCyclicBarrier {
    static class ChooseDishesFromMenu implements Runnable {
        private String who;
        private CyclicBarrier cyclicBarrier;

        public ChooseDishesFromMenu(String who, CyclicBarrier cyclicBarrier) {
            this.who = who;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println(this.who + " is coming...");
            Random random = new Random();
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(5)); // Simulate traffic time
            } catch (InterruptedException e) {
                System.out.println(this.who + " won't come...");
            }
            System.out.println(this.who + " arrived!");

            try {
                System.out.println(this.who + ": another " + this.cyclicBarrier.getNumberWaiting()
                        + " friends already arrived and is waiting..."); // This output may not be correct for race condition
                this.cyclicBarrier.await(); // wait for other friends
            } catch (BrokenBarrierException e) {
                System.out.println("Barrier Broken Exception.");
            } catch (InterruptedException e) {
                System.out.println("cyclicBarrier await() interrupted.");
            }
            System.out.println(this.who + ": All friends arrived! We can choose what we eat now !");
            // Here we can use countDownLatch to coordinate the chooseTask threads
        }
    }
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        ChooseDishesFromMenu tom = new ChooseDishesFromMenu("Tom", cyclicBarrier);
        ChooseDishesFromMenu jane = new ChooseDishesFromMenu("Jane", cyclicBarrier);
        ChooseDishesFromMenu felix = new ChooseDishesFromMenu("Felix", cyclicBarrier);

        executorService.execute(tom);
        executorService.execute(jane);
        executorService.execute(felix);

        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("awaitTermination interrupted.");
        }
        System.out.println("Done");
    }
}
