package helloworld.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public Object send(Message message, String host, int port) {
        // 1. Create a socket and connect to the server by host:port
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            // 2. Send the message to the server
            objectOutputStream.writeObject(message);
            // 3. Receive the message from the server
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("occur exception:", e);
        }
        return null;
    }

    public static void main(String[] args) {
        Client client = new Client();
        Message message = (Message)client.send(new Message("content from client"), "127.0.0.1", 6666);
        System.out.println("client receive message:" + message.getContent());
    }
}