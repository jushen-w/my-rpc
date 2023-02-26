package rpc;

import com.wjs.rpc.dto.RpcReq;
import com.wjs.rpc.dto.RpcResp;
import com.wjs.rpc.enumeration.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public Object doMethod(RpcReq req, Object service) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(req.getInterfaceName());
        } catch (ClassNotFoundException e) {
            RpcResp.fail(StatusCode.CLASS_NOT_FOUNT);
        }
        // 1. Validate the class called by the client
        if (!clazz.isAssignableFrom(service.getClass())) return RpcResp.fail(StatusCode.CLASS_NOT_FOUNT);
        // 2. Validate the method call by the client
        Method method = null;
        try {
            method = service.getClass().getMethod(req.getMethodName(), req.getParamTypes());
        } catch (NoSuchMethodException e) {
            return RpcResp.fail(StatusCode.METHOD_NOT_FOUND);
        }
        Object result = null;
        try {
             result = method.invoke(service, req.getParameters());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
