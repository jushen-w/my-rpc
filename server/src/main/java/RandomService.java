import com.wjs.rpc.test.IRandomService;
import com.wjs.rpc.test.RandomObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomService implements IRandomService {
    private static final Logger LOG = LoggerFactory.getLogger(RandomService.class);

    @Override
    public String hello(RandomObject object) {
        LOG.info("Received: {}", object.getData());
        return "Success, id = " + object.getId();
    }
}
