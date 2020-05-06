package keep.io;

import java.io.IOException;
import java.io.StringReader;

public class IOStringReader {
    public static void main(String[] args) throws IOException {
        StringReader stringReader = new StringReader(
                "Hello world."
        );
        int c;
        // Read character one by one.
        while ((c=stringReader.read()) != -1) {
            System.out.print((char)c);
        }
    }
}
