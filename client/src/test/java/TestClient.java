import com.wjs.rpc.test.IRandomService;
import com.wjs.rpc.test.RandomObject;
import org.w3c.dom.ls.LSOutput;
import rpc.RpcClientProxy;

public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("localhost", 9090);
        IRandomService service = proxy.getProxy(IRandomService.class);
        String result = service.hello(new RandomObject(1, "Hello World"));
        System.out.println(result);
    }
}
