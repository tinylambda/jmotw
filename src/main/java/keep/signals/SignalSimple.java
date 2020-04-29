package keep.signals;

import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.concurrent.TimeUnit;

public class SignalSimple {
    public static void main(String[] args) throws InterruptedException {
        SignalHandler intSignalHandler = new SignalHandler() {
            public void handle(Signal signal) {
                System.out.println("Get Signal: " + signal);
                System.out.println("Do some clean jobs");
                System.exit(0);
            }
        };

        // Use Ctrl + C to send INT signal to the process
        Signal.handle(new Signal("INT"), intSignalHandler);
        TimeUnit.MINUTES.sleep(1);
    }
}
