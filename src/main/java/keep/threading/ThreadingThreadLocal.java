package keep.threading;


import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class ShowThreadLocalTask implements Runnable {
    private Integer threadId;
    private ThreadLocal<Integer> threadLocalVar;

    public ShowThreadLocalTask(Integer threadId, ThreadLocal<Integer> threadLocalVar) {
        this.threadId = threadId;
        this.threadLocalVar = threadLocalVar;
    }

    public void run() {
        Random random = new Random();
        this.threadLocalVar.set(random.nextInt(1000));
        // threadLocalVar should be different in different threads
        System.out.println(
                this.threadId
                + "\t"
                + this.threadLocalVar.get()
                + "\t"
                + this.threadLocalVar.hashCode()
        );
    }
}

public class ThreadingThreadLocal {
    public static void main(String[] args) {
        ThreadLocal<Integer> threadLocalVar = new ThreadLocal<Integer>();
        threadLocalVar.set(9999);  // Should be 9999 all the time in main thread

        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0; i<5; i++) {
            executorService.execute(new ShowThreadLocalTask(i, threadLocalVar));
        }
        executorService.shutdown();

        // Wait threads to complete
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.out.print("Wait threads interrupted !");
        }

        // threadLocalVar should be 9999
        System.out.println(
                Thread.currentThread().getName()
                + "\t"
                + threadLocalVar.get()
                + "\t"
                + threadLocalVar.hashCode()
        );
    }
}
