package keep.io;

import java.io.*;

public class IOFileWriter {
    static String file = "/tmp/test2.txt";

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new StringReader("Hello world.")
        );

        PrintWriter printWriter = new PrintWriter(
          new BufferedWriter(new FileWriter(file))
        );

        int lineCount = 1;
        String s;
        while ((s=bufferedReader.readLine()) != null) {
            printWriter.println(lineCount++ + ": " + s);
        }
        printWriter.close();

        // Show the file content
        String content = IOFileReader.read(file);
        System.out.print(content);
    }
}
