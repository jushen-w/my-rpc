package helloworld.netty;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestByteBuffer {
    public static void main(String[] args) {
        // Automatically close the channel on final block.
        try (FileChannel channel = new FileInputStream("src/main/java/helloworld/netty/data.txt").getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(10); // 10-byte buffer
            // data --channel--> buffer
            while (true) {
                int len = channel.read(buffer); // buffer: write mode
                if (len == -1) break; // the channel has reached end-of-stream
                // print from the buffer
                buffer.flip(); // buffer: write mode (channel read) --flip--> read mode (channel write)
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    System.out.println((char)b);
                }
                buffer.clear(); // buffer: read mode --clear--> write mode
            }
        } catch (IOException e) {
        }
    }
}
