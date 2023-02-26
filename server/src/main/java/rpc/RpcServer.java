package rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.*;

public class RpcServer {

    private final ExecutorService threadPool;
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;
    private ServiceRegistry registry;

    public RpcServer(ServiceRegistry registry) {
        this.registry = registry;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workingQueue, threadFactory);
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Starting server...");
            Socket socket;
            while((socket = serverSocket.accept()) != null) {
                logger.info("Client connected, ip = " + socket.getInetAddress() + ", port = " + socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket, registry));
            }
        } catch (IOException e) {
            logger.error("Something wrong", e);
        } finally {
            threadPool.shutdown();
        }
    }

}
