package helloworld.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public void start(int port) {
        // 1. Create a socket object, bind to <port>, and start to listen.
        try (ServerSocket server = new ServerSocket(port)) {
            Socket request;
            // 2. Accept a request from a client. (block)
            while ((request = server.accept()) != null) {
                logger.info("client connected");
                try (ObjectInputStream objectInputStream = new ObjectInputStream(request.getInputStream());
                     ObjectOutputStream objectOutputStream = new ObjectOutputStream(request.getOutputStream())) {
                    // 3.Read the request message using ObjectInputStream. (deserialize)
                    Message message = (Message) objectInputStream.readObject();
                    logger.info("server receive message:" + message.getContent());
                    message.setContent("new content");
                    // 4. Send the response message using ObjectOutputStream. (serialize)
                    objectOutputStream.writeObject(message);
                    objectOutputStream.flush();
                } catch (IOException | ClassNotFoundException e) {
                    logger.error("occur exception:", e);
                }
            }
        } catch (IOException e) {
            logger.error("occur IOException:", e);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(6666);
    }
}