package keep.io;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class IODataInputStreamAvailable {
    public static void main(String[] args) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(
                new FileInputStream("/tmp/test.txt")
        );

        while (dataInputStream.available() != 0) {
            System.out.print((char) dataInputStream.readByte());
        }
    }
}
