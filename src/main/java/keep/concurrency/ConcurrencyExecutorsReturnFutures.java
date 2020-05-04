package keep.concurrency;

import java.util.ArrayList;
import java.util.concurrent.*;

public class ConcurrencyExecutorsReturnFutures {
    static class Worker implements Callable<Integer> {
        private int workerId;
        public Worker(int id) {
            this.workerId = id;
        }
        @Override
        public Integer call() throws Exception {
            return this.workerId * 100;
        }
    }
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayList<Future<Integer>> futures = new ArrayList<>();
        for(int i=0; i<10; i++) {
            futures.add(
                    executorService.submit(new Worker(i))
            );
        }

        for(Future<Integer> fs: futures) {
            try{
                System.out.println(fs.get()); // will block until completion
            } catch (InterruptedException e) {
                System.out.println(e);
                return;
            } catch (ExecutionException e) {
                System.out.println(e);
            } finally {
                executorService.shutdown();
            }
        }
    }
}
