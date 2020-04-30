package keep.time;

import java.time.LocalDateTime;

public class TimeLocalDateTimeGetCurrentDateTime {
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        // ISO8601 format
        System.out.println(localDateTime);
    }
}
