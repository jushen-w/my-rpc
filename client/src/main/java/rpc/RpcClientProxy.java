package rpc;

import com.wjs.rpc.dto.RpcReq;
import com.wjs.rpc.dto.RpcResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcClientProxy implements InvocationHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RpcClientProxy.class);

    private String host;
    private int port;

    public RpcClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        LOG.info("Proxy: Invoke method {" + method.getDeclaringClass().getName() + ":" + method.getName() + "}");
        String interfaceName = method.getDeclaringClass().getName();
        String methodName = method.getName();
        Class[] paramTypes = method.getParameterTypes();
        RpcReq rpcRequest = new RpcReq(interfaceName, methodName, args, paramTypes);
        RpcClient rpcClient = new RpcClient();
        return rpcClient.sendRequest(rpcRequest, host, port);
    }
}