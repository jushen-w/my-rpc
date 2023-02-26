import com.wjs.rpc.dto.RpcResp;
import com.wjs.rpc.test.IRandomService;
import com.wjs.rpc.test.RandomObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import rpc.RpcClientProxy;

public class TestClient {
    @Test
    void testInvoke() {
        RpcClientProxy proxy = new RpcClientProxy("localhost", 9090);
        IRandomService service = proxy.getProxy(IRandomService.class);
        String resp = service.hello(new RandomObject(1, "Hello World"));
        assertTrue("Success, id = 1".equals(resp));
    }
}
