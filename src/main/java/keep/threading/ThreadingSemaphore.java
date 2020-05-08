package keep.threading;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadingSemaphore {
    static class ResourcePool {
        private String[] resourcePool = new String[2];  // Only 2 resources can be used
        private ReentrantLock lock = new ReentrantLock(); // Used to coordinate the access to resourcePool

        public ResourcePool() {
            for(int i=0; i<resourcePool.length; i++) {
                try {
                    this.lock.lock();
                    resourcePool[i] = "free"; // free is available resource; busy is in-use resource
                } finally {
                    this.lock.unlock();
                }

            }
        }

        public String getResource() {
            Integer freeIndex = null;
            try {
                this.lock.lock();
                for (int i=0; i<resourcePool.length; i++) {
                    if (resourcePool[i].equals("free")) {
                        freeIndex = i;
                        break;
                    }
                }
                this.resourcePool[freeIndex] = "busy";
            } finally {
                this.lock.unlock();
            }
            return  "Resource(" + freeIndex + ")";
        }

        public void returnResource() {
            try {
                this.lock.lock();
                Integer busyIndex = null;
                for (int i=0; i<resourcePool.length; i++) {
                    if (resourcePool[i].equals("busy")) {
                        busyIndex = i;
                        break;
                    }
                }
                resourcePool[busyIndex] = "free";
            } finally {
                this.lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(2); // We have only 2 resources in resource pool, if more than 2 threads need the resource, just wait the resource.
        ResourcePool resourcePool = new ResourcePool();
        for (int i=0; i<10; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    String resource = resourcePool.getResource();
                    String threadName = Thread.currentThread().getName();
                    System.out.println(threadName + " -> Use (" + resource + ") to do some work");
                    Random random = new Random();
                    TimeUnit.SECONDS.sleep(random.nextInt(5));
                    System.out.println(threadName + " Done, return the resource to the pool");
                    resourcePool.returnResource();
                } catch (InterruptedException e) {
                    System.out.println("Interrupted in the thread use the resource");
                } finally {
                    semaphore.release();
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("Done");
    }
}
