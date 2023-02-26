package rpc;

public interface ServiceRegistry {
    // Register a service to the registry
    <T> void register(T service);
    Object getService(String serviceName);
}
