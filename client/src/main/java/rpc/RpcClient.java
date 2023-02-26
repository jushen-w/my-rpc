package rpc;

import com.wjs.rpc.dto.RpcReq;
import com.wjs.rpc.dto.RpcResp;
import com.wjs.rpc.enumeration.ExceptionCode;
import com.wjs.rpc.enumeration.StatusCode;
import com.wjs.rpc.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    public Object sendRequest(RpcReq rpcRequest, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            RpcResp resp =(RpcResp) objectInputStream.readObject();
            if (resp == null || resp.getStatusCode() == null || resp.getStatusCode() != StatusCode.SUCCESS.getCode())
                throw new RpcException(ExceptionCode.INVOCATION_FAILED, "{" + rpcRequest.getInterfaceName() + ": " + rpcRequest.getMethodName() +  "}");
            return resp.getData();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("ERROR: ", e);
            throw new RpcException(ExceptionCode.INVOCATION_FAILED, e);
        }
    }
}
