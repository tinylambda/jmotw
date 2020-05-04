package keep.concurrency;

import java.util.ArrayList;
import java.util.concurrent.*;

public class ConcurrencyExecutorsFutureCancel {
    static class Worker implements Callable<Integer> {
        private int workerId;
        public Worker(int id) {
            this.workerId = id;
        }
        @Override
        public Integer call() throws Exception {
            TimeUnit.SECONDS.sleep(1);
            return this.workerId * 100;
        }
    }
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ArrayList<Future<Integer>> futures = new ArrayList<>();
        for (int i=0; i<10; i++) {
            futures.add(
                    executorService.submit(new Worker(i))
            );
        }

        TimeUnit.SECONDS.sleep(1); // Allow the first 2 task completes (cannot be cancelled)
        for(int i=0; i<futures.size(); i++) {
            Future<Integer> fs = futures.get(i);
            // in-progress tasks are allowed to complete
            if(!fs.cancel(false)) {
                System.out.println(String.format("%d not cancelled", i));
            } else {
                System.out.println(String.format("%d cancelled", i));
            }
        }

        for(int i=0; i<futures.size(); i++) {
            Future<Integer> fs = futures.get(i);
            Integer result = fs.isCancelled() ? null: fs.get();
            System.out.println(
                    i +
                    " isCancelled: " + fs.isCancelled()
                    + " isDone: " + fs.isDone()
                    + " Result: " + result
            );
        }

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("done");
    }
}
