import rpc.RpcServer;

public class TestServer {
    public static void main(String[] args) {
        RpcServer server = new RpcServer();
        server.register(new RandomService(), 9090);
    }
}
