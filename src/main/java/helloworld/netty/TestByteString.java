package helloworld.netty;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static helloworld.util.ByteBufferUtil.debugAll;

public class TestByteString {
    public static void main(String[] args) {
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        // String -> byte[]
        buffer1.put("hello".getBytes()); // 1. Will not set position and limit pointers.
        debugAll(buffer1);
        buffer1.flip();
        System.out.println(StandardCharsets.UTF_8.decode(buffer1).toString());

        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello"); // 2.
        debugAll(buffer2);
        System.out.println(StandardCharsets.UTF_8.decode(buffer2).toString());

        ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes()); // 3.
        debugAll(buffer3);
        System.out.println(StandardCharsets.UTF_8.decode(buffer3).toString());

    }
}
