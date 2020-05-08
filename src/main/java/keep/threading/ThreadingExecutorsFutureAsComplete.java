package keep.threading;

import java.util.Random;
import java.util.concurrent.*;

public class ThreadingExecutorsFutureAsComplete {
    static class Worker implements Callable<Integer> {
        private int workerId;
        public Worker(int id) {
            this.workerId = id;
        }
        @Override
        public Integer call() throws Exception {
            Random random = new Random();
            TimeUnit.SECONDS.sleep(random.nextInt(2));
            return this.workerId;
        }
    }
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executorService);
        for(int i=0; i<10; i++) {
            completionService.submit(new Worker(i));
        }
        executorService.shutdown();

        for(int i=0; i<10; i++) {
            Integer result = completionService.take().get();
            if (result != null) {
                System.out.println(result);
            }
        }
    }
}
