package keep.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IOFileReader {
    public static String read(String filename) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(filename)
        );
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s=bufferedReader.readLine()) != null) {
            sb.append(s+"\n");
        }
        bufferedReader.close();
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(
                read("/tmp/test.txt")
        );
    }
}
