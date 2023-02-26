package rpc;

import com.wjs.rpc.enumeration.ExceptionCode;
import com.wjs.rpc.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceRegistryImpl implements ServiceRegistry {
    private static final Logger logger = LoggerFactory.getLogger(ServiceRegistryImpl.class);
    // {interface name: service}
    private final Map<String, Object> serviceInterfaceMap = new ConcurrentHashMap<>();
    // {service name}
    private final Set<String> serviceNameSet = ConcurrentHashMap.newKeySet();

    @Override
    public synchronized <T> void register(T service) {
        // "com.wjs.rpc.Service"
        String serviceName = service.getClass().getCanonicalName();
        if (serviceNameSet.contains(serviceName)) return;
        serviceNameSet.add(serviceName);
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0) throw new RpcException(ExceptionCode.SERVICE_NOT_FOUND);
        for (Class<?> a :interfaces) serviceInterfaceMap.put(a.getCanonicalName(), service);
        logger.info("Registered " + serviceName + " to the registry.");
    }

    @Override
    public synchronized Object getService(String serviceName) {
        Object service = serviceInterfaceMap.get(serviceName);
        if (service == null) throw new RpcException(ExceptionCode.SERVICE_NOT_FOUND);
        return service;
    }
}
