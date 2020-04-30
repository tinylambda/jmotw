package keep.time;

import java.util.concurrent.TimeUnit;

public class TimeMonotonicTime {
    public static void main(String[] args) throws Exception {
        // This method can only be used to measure elapsed time and is
        // not related to any other notion of system or wall-clock time.
        long start = System.nanoTime();
        TimeUnit.SECONDS.sleep(1);
        long end = System.nanoTime();
        System.out.println(String.format("start: %d", start));
        System.out.println(String.format("end: %d", end));
        System.out.println(String.format("span: %d", end - start));
    }
}
