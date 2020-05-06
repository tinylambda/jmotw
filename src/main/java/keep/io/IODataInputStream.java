package keep.io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

public class IODataInputStream {
    public static void main(String[] args) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(
          new ByteArrayInputStream("Hello world.".getBytes())
        );

        try {
            while (true) {
                System.out.print((char) dataInputStream.readByte());
            }
        } catch (EOFException e) {
            System.err.println("End of stream");
        }
    }
}
