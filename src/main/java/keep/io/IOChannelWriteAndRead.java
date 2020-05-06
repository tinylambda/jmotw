package keep.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class IOChannelWriteAndRead {
    private static int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws Exception{
        FileChannel fileChannel = new FileOutputStream("/tmp/test.txt").getChannel();
        fileChannel.write(ByteBuffer.wrap("Some text ".getBytes()));
        fileChannel.close();

        // Add to the end of the file
        fileChannel = new RandomAccessFile("/tmp/test.txt", "rw").getChannel();
        fileChannel.position(fileChannel.size()); // Move to the end
        fileChannel.write(ByteBuffer.wrap("Some more".getBytes()));
        fileChannel.close();

        // Read the file
        fileChannel = new FileInputStream("/tmp/test.txt").getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        fileChannel.read(byteBuffer);
        byteBuffer.flip();

        while (byteBuffer.hasRemaining()) {
            System.out.print((char)byteBuffer.get());
        }
    }
}
