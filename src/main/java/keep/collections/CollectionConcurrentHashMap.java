package keep.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CollectionConcurrentHashMap {
    public static void main(String[] args) throws Exception {
        ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>(); // won't miss keys
//        Map<String, Integer> map = new HashMap<>(); // Will miss some keys maybe

        Thread[] threads = new Thread[2];
        threads[0] = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<5; i++) {
                    map.put(String.format("key-a-%s", i), 0);
                }
            }
        });
        threads[1] = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<5; i++) {
                    map.put(String.format("key-b-%s", i), 0);
                }
            }
        });

        for (Thread thread: threads) {
            thread.start();
        }

        for (Thread thread: threads) {
            thread.join();
        }

        System.out.println(map);
    }
}
