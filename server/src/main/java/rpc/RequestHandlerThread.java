package rpc;

import com.wjs.rpc.dto.RpcReq;
import com.wjs.rpc.dto.RpcResp;
import com.wjs.rpc.enumeration.ExceptionCode;
import com.wjs.rpc.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandlerThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerThread.class);
    private final Socket socket;
    private final RequestHandler requestHandler = new RequestHandler();
    private final ServiceRegistry registry;

    public RequestHandlerThread(Socket socket, ServiceRegistry registry) {
        this.socket = socket;
        this.registry = registry;
    }
    @Override
    public void run() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcReq rpcReq = (RpcReq) objectInputStream.readObject();
            String interfaceName = rpcReq.getInterfaceName();
            Object service = registry.getService(interfaceName);
            if (service == null) {
                logger.error("Cannot find service: {}", interfaceName);
                throw new RpcException(ExceptionCode.SERVICE_NOT_REGISTERED);
            }
            // Invoke service method.
            Object result = requestHandler.doMethod(rpcReq, service);
            objectOutputStream.writeObject(RpcResp.success(result));
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Something Wrong: ", e);
        }
    }
}
