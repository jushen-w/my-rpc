import rpc.RpcServer;
import org.junit.jupiter.api.Test;
import rpc.ServiceRegistry;
import rpc.ServiceRegistryImpl;

import static org.junit.jupiter.api.Assertions.*;

public class TestServer {
    @Test
    void testServer(){
        // Register service
        ServiceRegistry registry = new ServiceRegistryImpl();
        registry.register(new RandomService());
        RpcServer server = new RpcServer(registry);
        server.start(9090);
    }
}
