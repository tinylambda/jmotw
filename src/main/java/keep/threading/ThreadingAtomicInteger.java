package keep.threading;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadingAtomicInteger {
    private static final Integer THREADS_COUNT = 20;
    private static Integer nonAtomicRace = 0;
    private static AtomicInteger atomicRace = new AtomicInteger(0);

    private static void nonAtomicIncrease() {
        nonAtomicRace += 1;
    }

    private static void atomicIncrease() {
        atomicRace.incrementAndGet();
    }

    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i=0; i<THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    for(int i=0; i<10000; i++) {
                        atomicIncrease();
                    }
                }
            });
        }

        for(Thread thread : threads) {
            thread.start();
        }

        for(Thread thread : threads) {
            thread.join();
        }

        System.out.println("Atomic increase result: " + atomicRace);

        for (int i=0; i<THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    for(int i=0; i<10000; i++) {
                        nonAtomicIncrease();
                    }
                }
            });
        }

        for(Thread thread : threads) {
            thread.start();
        }

        for(Thread thread : threads) {
            thread.join();
        }

        System.out.print("NonAtomic increase result: " + nonAtomicRace);
    }
}
